package scholl.both.analyzer.social.networks;

import static org.junit.Assert.assertEquals;

import scholl.both.analyzer.social.PostSet;
import scholl.both.analyzer.social.Post;
import scholl.both.analyzer.social.User;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;

public class TwitterClientTest {

    private static TwitterClient tc;
    
    @BeforeClass
    public static void setup() {
        tc = null;
        try {
            tc = new TwitterClient("tumblr_credentials.json");
        } catch (IOException e) {
            org.junit.Assert.assertTrue("Failure on creating client - IO exception:\n", false);
        }
        
        try {
            tc.authenticate();
        } catch (IOException e) {
            org.junit.Assert.assertTrue("Failure on authenticating client - IO exception:\n", false);
        }
    }

    @Test
    @Ignore("Too finicky")
    public void simpleTest() {
        User u = tc.getAuthenticatedUser();
        assertEquals("raptortech97", u.getTitle());
        
        for (User su : tc.getInterestingUsers()) {
            PostSet ps = su.getPosts(20);
            System.out.printf("%s: %n", su.getName());
            for (Post p : ps.toSet()) {
                System.out.printf("\t%s%n", p);
            }
        }
    }

    @Ignore("Sometimes this fails on travis. No clue why. Ignored for now.")
    @Test
    public void benPostTest() {
        System.out.println(tc.getRateLimit());
        User ben = tc.getUser("bkinderTARDIS42");
        PostSet ps = ben.getPosts(20);
        assertEquals(3, ps.size()); // Ben has 3 posts
    }
}
