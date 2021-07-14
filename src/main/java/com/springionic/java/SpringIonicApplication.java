package com.springionic.java;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springionic.java.domain.Categoria;
import com.springionic.java.domain.Cidade;
import com.springionic.java.domain.Cliente;
import com.springionic.java.domain.Endereco;
import com.springionic.java.domain.Estado;
import com.springionic.java.domain.ItemPedido;
import com.springionic.java.domain.Pagamento;
import com.springionic.java.domain.PagamentoComBoleto;
import com.springionic.java.domain.PagamentoComCartao;
import com.springionic.java.domain.Pedido;
import com.springionic.java.domain.Produto;
import com.springionic.java.domain.enums.EstadoPagamento;
import com.springionic.java.domain.enums.TipoCliente;
import com.springionic.java.repository.CategoriaRepository;
import com.springionic.java.repository.CidadeRepository;
import com.springionic.java.repository.ClienteRepository;
import com.springionic.java.repository.EnderecoRepository;
import com.springionic.java.repository.EstadoRepository;
import com.springionic.java.repository.ItemPedidoRepository;
import com.springionic.java.repository.PagamentoRepository;
import com.springionic.java.repository.PedidoRepository;
import com.springionic.java.repository.ProdutoRepository;

@SpringBootApplication
public class SpringIonicApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(SpringIonicApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		
	}

}
