/**
 * TransactionHistory.java
 *
 * Author: Linus Maina
 * Course: IFT/210
 * Project: Stock Portfolio Application
 * Description: Stores information about a single brokerage transaction.
 */
public class TransactionHistory {
    private String ticker;
    private String transDate;
    private String transType;
    private double qty;
    private double costBasis;

    /** Default constructor. */
    public TransactionHistory() {
        ticker = "";
        transDate = "";
        transType = "";
        qty = 0.0;
        costBasis = 0.0;
    }

    /** Overloaded constructor. */
    public TransactionHistory(String ticker, String transDate, String transType,
                              double qty, double costBasis) {
        this.ticker = ticker;
        this.transDate = transDate;
        this.transType = transType;
        this.qty = qty;
        this.costBasis = costBasis;
    }

    public String getTickerNjaramba() {
        return ticker;
    }

    public void setTickerNjaramba(String ticker) {
        this.ticker = ticker;
    }

    public String getTransDateNjaramba() {
        return transDate;
    }

    public void setTransDateNjaramba(String transDate) {
        this.transDate = transDate;
    }

    public String getTransTypeNjaramba() {
        return transType;
    }

    public void setTransTypeNjaramba(String transType) {
        this.transType = transType;
    }

    public double getQtyNjaramba() {
        return qty;
    }

    public void setQtyNjaramba(double qty) {
        this.qty = qty;
    }

    public double getCostBasisNjaramba() {
        return costBasis;
    }

    public void setCostBasisNjaramba(double costBasis) {
        this.costBasis = costBasis;
    }

    /**
     * Returns a formatted representation of the transaction.
     */
    public String toPrintNjaramba() {
        return String.format("%-15s %-8s %12.2f %15s %-10s",
                transDate, ticker, qty, String.format("$%.2f", costBasis), transType);
    }
}
