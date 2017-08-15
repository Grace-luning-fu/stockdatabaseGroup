package grace.osiris.stockmarketdb.controller;


import grace.osiris.stockmarketdb.models.Sales;
import grace.osiris.stockmarketdb.models.Stock;
import grace.osiris.stockmarketdb.repository.SalesRepo;
import grace.osiris.stockmarketdb.repository.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class MainController {

    double totalall=0;
    double subtotalall=0;
    double totaltax=0;

    @Autowired
    SalesRepo salesRepo;
    @Autowired
    StockRepo stockRepo;

    @GetMapping("/welcome")
    public String loadWelcome() {
        return "welcome";
    }

    @GetMapping("/addsale")
    public String loadProduct(Model model) {
        model.addAttribute("sales", new Sales());

        return "addsale";


    }

    @PostMapping("/addsale")
    public String processProduct(@Valid @ModelAttribute("sales") Sales sales, BindingResult bindingResult, Model model) {

        System.out.println(bindingResult.toString());

            if (bindingResult.hasErrors()) {
                return "addsale";
            }


       double unitprice=0;
            System.out.println(sales.getSalecode());

        //find the item in stock database
        Stock stock1 = new Stock();
        stock1= stockRepo.findStockByCode(sales.getSalecode());


        //get the price
        unitprice = stock1.getPrice();

        //get item name and price
      sales.setSaleprice(unitprice);
      sales.setSaleitem(stock1.getItem());

        System.out.println(unitprice);

        //calculate the new stock

        stock1.setQuantity(stock1.getQuantity()- sales.getSalequantity());

        //save the stock in stock database
        stockRepo.save(stock1);

        //calucate subtotal, tax, total
        sales.setTax(sales.taxcalc(unitprice, sales.getSalequantity()));
        sales.setSubtotal(sales.pretaxcalc(unitprice, sales.getSalequantity()));
        sales.setTotal(sales.aftertaxcalc(unitprice, sales.getSalequantity()));

        //add to the total of the total
        totalall += sales.getTotal();
        subtotalall += sales.getSubtotal();
        totaltax += sales.getTax();

        //add to the model to display later
        model.addAttribute("totalall", totalall);

        //save the transcation to the sales repo
        salesRepo.save(sales);

        return "result";

    }


    @GetMapping("/loadstock")
    public String LoadStock()
        {
        Iterable<Stock> stocklist;
        ArrayList<Stock> toadd = new ArrayList<>();

        Stock newProduct1 = new Stock();
        newProduct1.set("Bread", 8, 2.50, "bd203");

        Stock newProduct2 = new Stock();
        newProduct2.set("Apple Bag", 17, 5.50, "ab207");

        Stock newProduct3 = new Stock();
        newProduct3.set("Cookies Pack", 36, 9.25, "cp946");

        Stock newProduct4 = new Stock();
        newProduct4.set("Bananas Bag", 45, 12.60, "bn343");

        Stock newProduct5 = new Stock();
        newProduct5.set("Coffee Bag", 85, 5.10, "cb666");

        Stock newProduct6 = new Stock();
        newProduct6.set("Water", -3, 4.50, "wp852");

        toadd.add(newProduct1);
        toadd.add(newProduct2);
        toadd.add(newProduct3);
        toadd.add(newProduct4);
        toadd.add(newProduct5);
        toadd.add(newProduct6);

        stocklist = toadd;

        stockRepo.save(stocklist);

        return "loadconfirm";

    }

    @GetMapping("/displayall")
    public String displayall(Model model)
    {
     Iterable<Sales> allsales= salesRepo.findAll();
     model.addAttribute("allsales", allsales);

     model.addAttribute("totalall", totalall);
        model.addAttribute("totaltax", totaltax);
        model.addAttribute("subtotalall", subtotalall);
     return "displayall";


    }

    @GetMapping("/displaystock")
    public String displaystock(Model model) {
        Iterable<Stock> allstock = stockRepo.findAll();
        model.addAttribute("allstock", allstock);

        Iterable<Stock> posstock = stockRepo.findAllByQuantityGreaterThan(0);
        model.addAttribute("posstock", posstock);

        Iterable<Stock> negstock = stockRepo.findAllByQuantityLessThan(0);
        model.addAttribute("negstock", negstock);

        return "displaystock";

    }


}