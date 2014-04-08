package de.marrrschine.frontend;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

	ServiceConsumer serviceConsumer;
	Table table;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = true, ui = MyVaadinUI.class, widgetset = "de.marrrschine.frontend.AppWidgetSet")
	public static class Servlet extends VaadinServlet {
	}

	private void addLabel(VerticalLayout layout) {
		try {
			String otc = serviceConsumer.consumeOtcService();
			Label label = new Label("Next up: " + otc);
			label.addStyleName("fancylabel");
			layout.addComponent(label);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void createTable() throws ParseException {
		table.addContainerProperty("Rank", Long.class, null);
		table.addContainerProperty("Firstname", String.class, null);
		table.addContainerProperty("Lastname", String.class, null);
		table.addContainerProperty("Position", String.class, null);
		table.addContainerProperty("Rank in Position", Long.class, null);
		table.addContainerProperty("Class", String.class, null);
		table.addContainerProperty("School", String.class, null);
		table.addContainerProperty("Weight", String.class, null);
		table.addContainerProperty("Height", String.class, null);
		// BeanItemContainer<Person> container = new BeanItemContainer<Person>(Person.class);

		List<Prospect> prospectList = serviceConsumer.consumeServiceGet();
		BeanContainer<String, Prospect> dataContainer = new BeanContainer<String, Prospect>(Prospect.class);
		dataContainer.removeAllItems();
		dataContainer.setBeanIdProperty("rank");
		dataContainer.addAll(prospectList);
		this.table.setContainerDataSource(dataContainer);
	}

	@Override
	protected void init(VaadinRequest request) {
		serviceConsumer = new ServiceConsumer();
		final VerticalLayout layout = new VerticalLayout();
		setContent(layout);
		layout.setMargin(true);
		// Text field for inputting a filter
		final TextField tf = new TextField("Lastname Filter");
		tf.focus();
		this.addLabel(layout);
		layout.addComponent(tf);

		table = new Table("Available Prospects");

		try {
			this.createTable();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Filter table according to typed input
		tf.addTextChangeListener(new TextChangeListener() {
			Filter filter = null;

			public void textChange(TextChangeEvent event) {
				Filterable f = (Filterable) table.getContainerDataSource();

				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);

				// Set new filter for the "Name" column
				filter = new SimpleStringFilter("lastname", event.getText(), true, false);
				f.addContainerFilter(filter);
			}
		});

		table.setMultiSelect(true);
		table.setSortEnabled(true);
		table.setSelectable(true);
		table.setVisibleColumns(new Object[] { "rank", "firstname", "lastname", "pos", "posRank", "school",
				"classYear", "height", "weight" });
		layout.addComponent(table);
	}

}
