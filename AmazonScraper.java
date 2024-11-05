
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.util.Elements;
import javax.swing.text.Document;
import javax.swing.text.Element;

import sun.jvm.hotspot.utilities.soql.JSMap;


public class AmazonScraper {

    public static void main(String[] args) throws IOException {
        String url = "https://www.amazon.com/s?k=iphone+14&ref=nb_sb_noss";

        try {
            Document document = JSMap.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36").get();


            Elements products = ((Object) document).select("div[data-component-type='s-search-result']");

            List<String[]> productData = new ArrayList<>();
            productData.add(new String[]{"Product Name", "Price", "Rating"});

            for (Element product : products) {
                String productName = product.selectFirst("span.a-size-base-plus.a-color-base.a-text-normal").text();
                String productPrice = product.selectFirst("span.a-price-whole").text();
                String productRating = ((Object) product).selectFirst("span.a-icon-alt").text();

                productData.add(new String[]{productName, productPrice, productRating});
            }

            FileWriter csvWriter = new FileWriter("amazon_products.csv");
            for (String[] product : productData) {
                csvWriter.append(String.join(",", product));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();

            System.out.println("Data scraped and saved to amazon_products.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}