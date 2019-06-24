package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager manager;

	public BarChartModel getVendasModel() {

	    BarChartModel model = new BarChartModel();

	    ChartSeries vendaSerie = new ChartSeries();
	    vendaSerie.setLabel("Vendas");
	    
	    model.setTitle("Vendas");
	    model.setLegendPosition("ne");
	    model.setAnimate(true);

	    Axis xAxis = model.getAxis(AxisType.X);
	    xAxis.setLabel("T�tulo");

	    Axis yAxis = model.getAxis(AxisType.Y);
	    yAxis.setLabel("Quantidade");

	    List<Venda> vendas = getVendas();

	    for (Venda venda : vendas) {
	        vendaSerie.set(venda.getLivro().getTitulo(), venda.getQuantidade());
	    }

	    model.addSeries(vendaSerie);

	    return model;
	}

	public List<Venda> getVendas() {

	    List<Venda> vendas = this.manager.createQuery("select v from Venda v", Venda.class).getResultList();

	    return vendas;
	}
}