import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;

public class QuizzAutomation {

    static WebDriver driver;
    static FileWriter logWriter;

    public static void main(String[] args) throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logWriter = new FileWriter("selenium-log.txt", false);

        log("===== QUIZ AUTOMATION STARTED =====");

        String path = "file:///C:/Users/vch44/IdeaProjects/Chennaboina.vishal_ParulUniversity_2203031240260/quiz.html";
        driver.get(path);
        Thread.sleep(1000);

        takeScreenshot("01_Landing_Page.png");
        log("Loaded Quiz Page");
        log("Page Title: " + driver.getTitle());
        log("Page URL: " + driver.getCurrentUrl());


        WebElement startBtn = driver.findElement(By.id("startBtn"));
        startBtn.click();
        Thread.sleep(1500);

        takeScreenshot("02_Quiz_Started.png");
        log("Quiz Started — Question 1 displayed");


        for (int i = 1; i <= 5; i++) {

            log("Answering Question " + i);
            takeScreenshot("Question_" + i + ".png");

            WebElement choice = driver.findElement(By.xpath("//div[@class='choice'][3]"));
            choice.click();
            Thread.sleep(800);

            if (i < 5) {
                driver.findElement(By.id("nextBtn")).click();
                Thread.sleep(1200);
            }
        }


        driver.findElement(By.id("submitBtn")).click();
        Thread.sleep(1500);

        takeScreenshot("06_Result_Page.png");
        log("Quiz Submitted. Results displayed.");


        String score = driver.findElement(By.id("scoreText")).getText();
        String correct = driver.findElement(By.id("correctCount")).getText();
        String wrong = driver.findElement(By.id("wrongCount")).getText();

        log("Score shown: " + score);
        log("Correct answers: " + correct);
        log("Wrong answers: " + wrong);

        log("===== QUIZ AUTOMATION COMPLETED SUCCESSFULLY =====");

        logWriter.close();
        driver.quit();
    }


    public static void takeScreenshot(String filename) {
        try {
            File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            File destDir = new File("screenshots");
            if (!destDir.exists()) destDir.mkdir();

            File destFile = new File(destDir, filename);
            FileUtils.copyFile(scr, destFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void log(String message) throws IOException {
        logWriter.write(message + "\n");
        System.out.println(message);
    }
}
