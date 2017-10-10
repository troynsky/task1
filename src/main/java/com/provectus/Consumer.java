package com.provectus;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Future;

public interface Consumer {

    boolean consume(Solution j);

    void finishConsumption();

    List<Future<BigDecimal>> getResult();

}
