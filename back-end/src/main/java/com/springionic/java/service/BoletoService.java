package com.springionic.java.service;

import com.springionic.java.domain.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {

    public void preencherPagamentoComBoleto(PagamentoComBoleto pagamentoComBoleto, Date instanteDePedido) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(instanteDePedido);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        pagamentoComBoleto.setDataVencimento(cal.getTime() );
    }
}
