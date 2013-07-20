package org.smartparam.engine.config;

import org.smartparam.engine.core.engine.ParamEngine;
import org.smartparam.engine.core.engine.ParamPreparer;
import org.smartparam.engine.core.engine.SmartParamEngine;
import org.smartparam.engine.core.repository.InvokerRepository;
import org.smartparam.engine.core.repository.MatcherRepository;
import org.smartparam.engine.core.repository.TypeRepository;
import org.smartparam.engine.core.service.FunctionManager;
import org.smartparam.engine.core.service.FunctionProvider;
import org.smartparam.engine.core.service.ParameterProvider;

/**
 *
 * @author Adam Dubiel <dubiel.adam@gmail.com>
 */
public class ParamEngineFactory {

    public ParamEngine createParamEngine(ParamEngineConfig config) {
        prepareInitializerRunner(config);

        ParamEngine engine = new SmartParamEngine();

        engine.setFunctionManager(prepareFunctionManager(config));
        engine.setParamPreparer(prepareParamPreparer(config));

        config.getInitializationRunner().runInitializers(engine);

        return engine;
    }

    private void prepareInitializerRunner(ParamEngineConfig config) {
        if (config.getInitializationRunner() == null) {
            ComponentInitializerRunner initializerRunner = new BasicComponentInitializerRunner(config.getComponentInitializers());
            config.setInitializationRunner(initializerRunner);
        }
    }

    private FunctionManager prepareFunctionManager(ParamEngineConfig config) {
        FunctionManager functionManager = config.getFunctionManager();
        functionManager.setFunctionProvider(prepareFunctionProvider(config));
        functionManager.setInvokerRepository(prepareInvokerRepository(config));

        config.getInitializationRunner().runInitializers(functionManager);

        return functionManager;
    }

    private FunctionProvider prepareFunctionProvider(ParamEngineConfig config) {
        FunctionProvider functionProvider = config.getFunctionProvider();
        functionProvider.setFunctionCache(config.getFunctionCache());
        functionProvider.registerAll(config.getFunctionRepositories());

        config.getInitializationRunner().runInitializers(functionProvider);
        config.getInitializationRunner().runInitializersOnList(functionProvider.registeredItems().values());

        return functionProvider;
    }

    private InvokerRepository prepareInvokerRepository(ParamEngineConfig config) {
        InvokerRepository invokerRepository = config.getInvokerRepository();
        invokerRepository.registerAll(config.getFunctionInvokers());

        config.getInitializationRunner().runInitializers(invokerRepository);

        return invokerRepository;
    }

    private ParamPreparer prepareParamPreparer(ParamEngineConfig config) {
        ParamPreparer paramPreparer = config.getParamPreparer();
        paramPreparer.setParameterProvider(prepareParameterProvider(config));
        paramPreparer.setFunctionProvider(config.getFunctionProvider());
        paramPreparer.setParamCache(config.getParamCache());
        paramPreparer.setMatcherRepository(prepareMatcherRepository(config));
        paramPreparer.setTypeRepository(prepareTypeRepository(config));

        config.getInitializationRunner().runInitializers(paramPreparer);

        return paramPreparer;
    }

    private ParameterProvider prepareParameterProvider(ParamEngineConfig config) {
        ParameterProvider parameterProvider = config.getParameterProvider();
        parameterProvider.registerAll(config.getParameterRepositories());

        config.getInitializationRunner().runInitializers(parameterProvider);
        config.getInitializationRunner().runInitializersOnList(parameterProvider.registeredItems().values());

        return parameterProvider;
    }

    private MatcherRepository prepareMatcherRepository(ParamEngineConfig config) {
        MatcherRepository matcherRepository = config.getMatcherRepository();
        matcherRepository.registerAll(config.getMatchers());

        config.getInitializationRunner().runInitializers(matcherRepository);

        return matcherRepository;
    }

    private TypeRepository prepareTypeRepository(ParamEngineConfig config) {
        TypeRepository typeRepository = config.getTypeRepository();
        typeRepository.registerAll(config.getTypes());

        config.getInitializationRunner().runInitializers(typeRepository);

        return typeRepository;
    }
}