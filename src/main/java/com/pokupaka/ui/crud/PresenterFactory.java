/**
 *
 */
package com.pokupaka.ui.crud;

import com.pokupaka.app.security.CurrentUser;
import com.pokupaka.backend.data.entity.Order;
import com.pokupaka.backend.service.ProductService;
import com.pokupaka.ui.views.products.ProductsView;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

//@Configuration
public class PresenterFactory {

	/*@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public EntityPresenter<Order, ProductsView> orderEntityPresenter(ProductService crudService, CurrentUser currentUser) {
		return new EntityPresenter<>(crudService, currentUser);
	}*/

}
