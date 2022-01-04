package com.ecocustomerapp.di.component;


import com.ecocustomerapp.di.module.ServiceModule;
import com.ecocustomerapp.di.scope.ServiceScope;
import com.ecocustomerapp.utils.location.LocationUpdatesService;

import dagger.Component;

@ServiceScope
@Component(modules = ServiceModule.class, dependencies = AppComponent.class)
public interface ServiceComponent {
    void inject(LocationUpdatesService service);
}
