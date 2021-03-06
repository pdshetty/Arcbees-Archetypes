/**
 * Copyright 2012 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.project.client.gin;

import javax.inject.Inject;

import com.arcbees.project.client.application.ApplicationModule;
import com.arcbees.project.client.place.NameTokens;
import com.arcbees.project.client.request.MyRequestFactory;
import com.google.gwt.core.client.GWT;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.gwtplatform.mvp.client.proxy.DefaultPlaceManager;

public class ClientModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new DefaultModule(DefaultPlaceManager.class));
        install(new ApplicationModule());

        // DefaultPlaceManager Places
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.home);
        bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.home);
        bindConstant().annotatedWith(UnauthorizedPlace.class).to(NameTokens.home);

        bind(MyRequestFactory.class).toProvider(RequestFactoryProvider.class).in(Singleton.class);
    }

    static class RequestFactoryProvider implements Provider<MyRequestFactory> {
        private final MyRequestFactory requestFactory;

        @Inject
        RequestFactoryProvider(EventBus eventBus) {
            requestFactory = GWT.create(MyRequestFactory.class);
            requestFactory.initialize(eventBus);
        }

        public MyRequestFactory get() {
            return requestFactory;
        }
    }
}
