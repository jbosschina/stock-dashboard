package com.vaadin.demo.dashboard;

import org.jsoup.nodes.Element;

import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;

public class DashboardSessionInitListener implements SessionInitListener {

    private static final long serialVersionUID = -6284372701610626825L;

    @Override
    public final void sessionInit(final SessionInitEvent event) throws ServiceException {

        event.getSession().addBootstrapListener(new BootstrapListener() {

            private static final long serialVersionUID = 2747518844811658729L;

            @Override
            public void modifyBootstrapPage(final BootstrapPageResponse response) {
                final Element head = response.getDocument().head();
                head.appendElement("meta").attr("name", "viewport").attr("content", "width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no");
                head.appendElement("meta").attr("name", "apple-mobile-web-app-capable").attr("content", "yes");
                head.appendElement("meta").attr("name", "apple-mobile-web-app-status-bar-style").attr("content", "black-translucent");

                String contextPath = response.getRequest().getContextPath();
                head.appendElement("link").attr("rel", "apple-touch-icon").attr("href", contextPath + "/VAADIN/themes/dashboard/img/app-icon.png");
            }

            @Override
            public void modifyBootstrapFragment(
                    final BootstrapFragmentResponse response) {
            }
        });
    }

}
