package com.framework.page;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BasePageFactory {

    public static <T extends BasePage> T createInstance(final Class<T> page) {
        try {
            BasePage basePage = page.getDeclaredConstructor().newInstance();
            return page.cast(basePage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new NullPointerException("Page class instantiation failed.");
    }


}
