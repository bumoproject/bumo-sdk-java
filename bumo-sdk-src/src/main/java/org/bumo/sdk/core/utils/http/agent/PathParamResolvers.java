package org.bumo.sdk.core.utils.http.agent;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PathParamResolvers {

	/**
	 * Null path parameter parser
	 */
	public static final PathParamResolver NONE_PATH_PARAM_RESOLVER = new NonePathParamResolver();

	/**
	 * A path parameter parser is created based on the specified path parameters
	 * 
	 * @param paramDefinitions
	 * @return
	 */
	public static PathParamResolver createResolver(List<ArgDefEntry<PathParamDefinition>> paramDefinitions) {
		return new ArgArrayPathParamResolver(paramDefinitions);
	}

	private static class ArgArrayPathParamResolver implements PathParamResolver {

		private List<ArgDefEntry<PathParamDefinition>> paramDefinitions;

		public ArgArrayPathParamResolver(List<ArgDefEntry<PathParamDefinition>> paramDefinitions) {
			this.paramDefinitions = paramDefinitions;
		}

		@Override
		public Map<String, String> resolve(Object[] args) {
			Map<String, String> pathParams = new HashMap<String, String>();
			String name;
			String value;
			for (ArgDefEntry<PathParamDefinition> paramDef : paramDefinitions) {
				name = paramDef.getDefinition().getName();
				value = paramDef.getDefinition().getConverter().toString(args[paramDef.getIndex()]);
				pathParams.put(name, value);
			}
			return pathParams;
		}
	}

	private static class NonePathParamResolver implements PathParamResolver {

		@SuppressWarnings("unchecked")
		@Override
		public Map<String, String> resolve(Object[] args) {
			return Collections.EMPTY_MAP;
		}

	}
}
