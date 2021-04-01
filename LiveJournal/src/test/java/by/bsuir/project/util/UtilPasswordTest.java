package by.bsuir.project.util;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UtilPasswordTest {

    @DataProvider(name = "hashPassword")
    public Object[] loginPass() {

        return new Object[][]{
                {"1234"}, //user1
                {"1two345"}, //user2
                {"1234five"}, //user3
                {"4321"}, //user4
                {"888"}, //Vlad
        };
    }

    @Test(dataProvider = "hashPassword")
    public void hashPasswordTest(String password) {
        String result = UtilPassword.hashPassword(password);
//        System.out.println(result);
        Assert.assertNotNull(result);
    }

}