package com.discordbot.LocalUtils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ArrayObjectUtilTest {

    @Test
    public void convertObjectToList() {
        ArrayList<String> data = new ArrayList<>();
        data.add("abc");
        data.add("def1");
        Object[] toParse = data.toArray();

        List<?> returned = ArrayObjectUtil.convertObjectToList(toParse);
        Assert.assertNotNull(returned);
        Assert.assertEquals("abc", returned.get(0));
        Assert.assertEquals("def1", returned.get(1));
    }
}