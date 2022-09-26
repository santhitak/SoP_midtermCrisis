package com.example.sop_midterm.view;

import com.example.sop_midterm.pojo.Product;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Route("")
public class ProductView extends VerticalLayout {

    @Autowired
    private RabbitTemplate rabbit;

    private Product newProduct;

    ComboBox<String> productList = new ComboBox<>("Product List");
    TextField productName = new TextField("Product Name");
    NumberField productCost = new NumberField("Product Cost");
    NumberField productProfit = new NumberField("Product Profit");
    NumberField productPrice = new NumberField("Product Price");
    Button add = new Button("Add Product");
    Button update = new Button("Update Product");
    Button del = new Button("Delete Product");
    Button clear = new Button("Clear Product");


    public ProductView(){
        newProduct = new Product();
        VerticalLayout container = new VerticalLayout();
        HorizontalLayout btn = new HorizontalLayout();

        productList.setWidthFull();
        productList.setItems("");
        productList.setPlaceholder("Product Name");
        productName.setWidthFull();
        productCost.setWidthFull();
        productProfit.setWidthFull();
        productPrice.setWidthFull();
        productCost.setValue(0.0);
        productPrice.setValue(0.0);
        productProfit.setValue(0.0);
        productPrice.setEnabled(false);

        productList.addFocusListener(event -> {
            if (productName != null){
                List<Product> products = (List<Product>) rabbit.convertSendAndReceive("ProductExchange", "getall", "");
                ArrayList<String> productNameList = new ArrayList<>();

                for (Product product : products) {
                    productNameList.add(product.getProductName());
                }

                productList.setItems(productNameList);
            }
        });

        productList.addValueChangeListener(event -> {
            System.out.println(productList.getValue());
            newProduct = (Product) rabbit.convertSendAndReceive("ProductExchange", "getname", productList.getValue());
            productProfit.setValue(newProduct.getProductProfit());
            productCost.setValue(newProduct.getProductCost());
            productName.setValue(newProduct.getProductName());
            productPrice.setValue(newProduct.getProductPrice());
        });

        add.addClickListener(e -> action("add"));
        update.addClickListener(e -> action("update"));
        del.addClickListener(e -> rabbit.convertSendAndReceive("ProductExchange", "delete", newProduct));
        clear.addClickListener(e -> {
            productCost.setValue(0.0);
            productProfit.setValue(0.0);
            productName.setValue("");
            productPrice.setValue(0.0);

            new Notification("Cleared Data", 500).open();
        });

        productCost.addKeyPressListener(e -> {
           if  (e.getKey().toString().equals("Enter")) refreshPrice();
        });

        productProfit.addKeyPressListener(e -> {
            if  (e.getKey().toString().equals("Enter")) refreshPrice();
        });
        btn.add(add, update, del, clear);
        container.add(productList, productName, productCost, productProfit, productPrice, btn);
        container.setWidth("600px");
        container.setJustifyContentMode(JustifyContentMode.CENTER);
        container.setAlignItems(Alignment.CENTER);
        add(container);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
    }

    public void refreshPrice() {
        productPrice.setValue(WebClient
                .create()
                .get()
                .uri("http://localhost:8080/getPrice/" + productCost.getValue() + "/" + productProfit.getValue())
                .retrieve()
                .bodyToMono(Double.class)
                .block());
    }
    public void action(String key){
        refreshPrice();
        String id = newProduct.getId();
        String name = productName.getValue();
        Double cost = productCost.getValue();
        Double profit = productProfit.getValue();
        Double price = productPrice.getValue();

        Product product_action = new Product(key.equals("update") ? id : null, name, cost, profit, price);

        new Notification((key.equals("add") ? "Add" : "Update") + " 300% Success", 500).open();

        rabbit.convertSendAndReceive("ProductExchange", key, product_action);
    }
}
