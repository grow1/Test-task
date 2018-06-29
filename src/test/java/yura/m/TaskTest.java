package yura.m;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;


public class TaskTest {
    public static WebDriver d;

    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        d = new ChromeDriver();
        d.get("https://en.wikipedia.org/wiki/To_Kill_a_Dragon");

        WebElement casts = d.findElement(By.cssSelector("#mw-content-text > div > ul"));
        List<WebElement> links = casts.findElements(By.tagName("li"));

        for (int i = 0; i < links.size(); i++) {

            casts = d.findElement(By.cssSelector("#mw-content-text > div > ul"));
            links = casts.findElements(By.tagName("li"));

            String ActorName = links.get(i).getText();
            ActorName= ActorName.substring(0, ActorName.indexOf('-'));

            String my_new_str = ActorName.replace(" ", "_");
            String final_str = my_new_str.substring(0, my_new_str.length()-1);

            d.get("https://en.wikipedia.org/wiki/" + final_str);
            d.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

            if (d.findElements(By.id("Selected_filmography")).size() > 0) {

                Assert.assertEquals(d.findElement(By.linkText("To Kill a Dragon")).getText(), "To Kill a Dragon");
                System.out.println(final_str + " played in To Kill a Dragon film");

                Thread.sleep(1000);
                d.quit();
            } else if (d.findElements(By.id("Filmography")).size() > 0) {
                WebElement element2 = d.findElement(By.id("Filmography"));
                ((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView(true);", element2);
                Thread.sleep(500);

                Assert.assertEquals(d.findElement(By.linkText("To Kill a Dragon")).getText(), "To Kill a Dragon");
                System.out.println(final_str + " played in To Kill a Dragon film");

                Thread.sleep(1000);
                d.quit();
            } else System.out.println(links.get(i).getText() + " is not an Actor");

            d = new ChromeDriver();
            d.get("https://en.wikipedia.org/wiki/To_Kill_a_Dragon");
        }
        d.quit();

    }
}

