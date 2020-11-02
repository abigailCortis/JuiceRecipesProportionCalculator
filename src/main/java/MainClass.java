import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter juice recipe name: ");
        String juiceName = scanner.nextLine();

        System.setProperty("webdriver.chrome.driver", "C:/Users/Dell/Desktop/JuiceRecipesProportionCalculator/chromedriver.exe");

        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://juicerecipes.com");

        webDriver.findElement(By.xpath("/html/body/header/div/nav/div/div[1]/button")).click();

        webDriver.findElement(By.id("id_q")).sendKeys(juiceName);

        webDriver.findElement(By.id("id_q")).submit();

        webDriver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[1]/h3/a")).click();

        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("scroll(0,1000)");

        String yieldsString = webDriver.findElement(By.xpath("/html/body/main/article/div/div[2]/section[1]/div[1]/div/span")).getText();
        String yieldValue = yieldsString.substring(7, 9);
        double yieldVal = Double.parseDouble(yieldValue);

        List<WebElement> ingredientAmounts = webDriver.findElements(By.cssSelector("div.ingredient-group > span.label.label-default.pull-right"));

        List<String> amounts = new ArrayList<String>();

        for(int i=0; i<ingredientAmounts.size(); i++){
            String amount = ingredientAmounts.get(i).getText();
            amount = amount.substring(0, amount.length()-1);
            double amt= Double.parseDouble(amount);

            double newAmt = 33/yieldVal * amt;

            String newAmount = String.valueOf(newAmt).concat("g");
            amounts.add(newAmount);
        }

        List<WebElement> ingredients = webDriver.findElements(By.className("ingredient-name"));

        for(int i=0; i<ingredients.size(); i++){
            System.out.println(ingredients.get(i).getText()+"\t"+amounts.get(i));
        }

        webDriver.quit();
    }
}
