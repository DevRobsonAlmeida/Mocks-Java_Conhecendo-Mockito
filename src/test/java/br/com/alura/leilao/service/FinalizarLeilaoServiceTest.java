/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.alura.leilao.service;

import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author 99030499
 */
public class FinalizarLeilaoServiceTest {

    private FinalizarLeilaoService service;

    @Mock
    private LeilaoDao leilaoDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.service = new FinalizarLeilaoService(leilaoDao);
    }

    @Test
    public void testFinalizarLeilao() {
        List<Leilao> leiloes = leiloes();
        
        Mockito.when(leilaoDao.buscarLeiloesExpirados())
               .thenReturn(leiloes);
        
        service.finalizarLeiloesExpirados();
        
        Leilao leilao = leiloes.get(0);
        Assert.assertTrue(leilao.isFechado());
        Assert.assertEquals(new BigDecimal("900"),
                leilao.getLanceVencedor().getValor());
        Mockito.verify(leilaoDao).salvar(leilao);

    }
    
    private List<Leilao> leiloes(){
        List<Leilao> lista = new ArrayList<>();
        
        Leilao leilao = new Leilao("Celular"
                , new BigDecimal("500")
                , new Usuario("fulano"));
        
        Lance primeiro = new Lance(new Usuario("Beltrano"),
                new BigDecimal("600"));
        Lance segundo = new Lance(new Usuario("CiClano"),
                new BigDecimal("900"));
        
        leilao.propoe(primeiro);
        leilao.propoe(segundo);
        
        lista.add(leilao);
        
        return lista;
    }

}
