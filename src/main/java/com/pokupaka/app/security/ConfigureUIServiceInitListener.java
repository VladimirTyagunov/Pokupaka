package com.pokupaka.app.security;

import com.pokupaka.ui.exceptions.AccessDeniedException;
import com.pokupaka.ui.views.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.spring.annotation.SpringComponent;

/**
 * Adds before enter listener to check access to views.
 * Adds the Offline banner.
 * 
 */
@SpringComponent
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

	@Override
	public void serviceInit(ServiceInitEvent event) {
		System.out.println(" serviceInit ConfigureUIServiceInitListener ");
		event.getSource().addUIInitListener(uiEvent -> {
			final UI ui = uiEvent.getUI();
			//ui.add(new OfflineBanner());
			ui.addBeforeEnterListener(this::beforeEnter);
		});
	}

	/**
	 * Reroutes the user if she is not authorized to access the view.
	 *
	 * @param event
	 *     before navigation event with event details
	 */
	private void beforeEnter(BeforeEnterEvent event) {
		final boolean accessGranted = SecurityUtils.isAccessGranted(event.getNavigationTarget());
		System.out.println("checking an access " + event.getNavigationTarget());
		if (!accessGranted) {
			System.out.println("access not gt to " + event.getNavigationTarget());
			if (SecurityUtils.isUserLoggedIn()) {
				event.rerouteToError(AccessDeniedException.class);
			} else {
				event.rerouteTo(LoginView.class);
			}
		}
	}
}
