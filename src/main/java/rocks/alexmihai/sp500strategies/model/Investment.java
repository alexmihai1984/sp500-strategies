package rocks.alexmihai.sp500strategies.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Investment {

    public enum Type {
        INVESTMENT,
        DIV
    }

    private Sp500Value value;
    private double amount;
    private Type type;

    public double getUnits() {
        return this.amount / this.value.getPrice();
    }
}
