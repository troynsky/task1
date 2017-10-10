package com.provectus;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public class App {
    public static void main(String[] args) {

        Consumer consumer = new ConsumerImpl(4);

        consumer.consume(new CalcPi(1000));
        consumer.consume(new CalcPi(1001));
        consumer.consume(new CalcPi(1002));
        consumer.consume(new CalcPi(1003));

        consumer.finishConsumption();

        consumer.getResult().forEach(r -> {
                    try {
                        System.out.println(r.get().toString());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
        );


    }

    static class CalcPi implements Solution {

        private int numberOfCorrectDigits = 1;

        public CalcPi(int numberOfCorrectDigits) {
            this.numberOfCorrectDigits = numberOfCorrectDigits;
        }

        public BigDecimal calculatePi(int numberOfCorrectDigits) {
            if (numberOfCorrectDigits == 0) {
                numberOfCorrectDigits = this.numberOfCorrectDigits;
            }

            BigDecimal pi = new BigDecimal(0);

            for (int i = 0; i < numberOfCorrectDigits; i++) {
                pi = pi.add(BigDecimal.valueOf(Math.pow(-1, i) / (2 * i + 1)));
            }
            pi = pi.multiply(BigDecimal.valueOf(4));

            return pi;
        }
    }
}
