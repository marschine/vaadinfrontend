package de.marrrschine.frontend;

import javax.servlet.annotation.WebServlet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "de.marrrschine.frontend.AppWidgetSet")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		 final VerticalLayout layout = new VerticalLayout();
		 layout.setMargin(true);
		 setContent(layout);

		// Handle the events with an anonymous class

		ServiceConsumer serviceConsumer = new ServiceConsumer();
		final Table table = new Table("Available Prospects");
		table.addContainerProperty("Firstname", String.class, null);
		table.addContainerProperty("Lastname", String.class, null);
		// table.addItem(new Object[]{"bla", "blub"}, 1);
		// table.addItem(new Object[]{"blfdsafdsa", "blubfds"}, 1);
		// table.setPageLength(1);
		JSONArray jsonArray;
		try {
			jsonArray = serviceConsumer.consumeServiceGet();
			int i = 0;
			for (Object object : jsonArray) {
				i++;
				JSONObject jsonObj = (JSONObject) object;
				String firstname = (String) jsonObj.get("firstname");
				String lastname = (String) jsonObj.get("lastname");
				table.addItem(new Object[] { firstname, lastname }, i);
				table.setPageLength(i);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		layout.addComponent(table);
	}

}
