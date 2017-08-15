package grace.osiris.stockmarketdb.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @NotNull
@Size(min=2)
private String saleuser;

@NotNull
@Size(min=2)
private String salecode;


private String saleitem;

@NotNull
@Min(1)
private int salequantity;


private double saleprice;


private double subtotal;

private double total;



    private double tax;



    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSaleuser() {
        return saleuser;
    }

    public void setSaleuser(String saleuser) {
        this.saleuser = saleuser;
    }

    public String getSalecode() {
        return salecode;
    }

    public void setSalecode(String salecode) {
        this.salecode = salecode;
    }

    public String getSaleitem() {
        return saleitem;
    }

    public void setSaleitem(String saleitem) {
        this.saleitem = saleitem;
    }

    public int getSalequantity() {
        return salequantity;
    }

    public void setSalequantity(int salequantity) {
        this.salequantity = salequantity;
    }

    public double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(double saleprice) {
        this.saleprice = saleprice;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    public double taxcalc(double price, int quant){

        double tax=(price*quant)*0.06;

        return tax;
    }

    public double pretaxcalc(double price, int quant){

        double pribeforetax=(price*quant);

        return pribeforetax;
    }

    public double aftertaxcalc(double price, int quant){

        double priaftertax=(price*quant)*1.06;

        return priaftertax;
    }


}
