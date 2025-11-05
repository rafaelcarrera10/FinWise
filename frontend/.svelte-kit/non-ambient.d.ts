
// this file is generated — do not edit it


declare module "svelte/elements" {
	export interface HTMLAttributes<T> {
		'data-sveltekit-keepfocus'?: true | '' | 'off' | undefined | null;
		'data-sveltekit-noscroll'?: true | '' | 'off' | undefined | null;
		'data-sveltekit-preload-code'?:
			| true
			| ''
			| 'eager'
			| 'viewport'
			| 'hover'
			| 'tap'
			| 'off'
			| undefined
			| null;
		'data-sveltekit-preload-data'?: true | '' | 'hover' | 'tap' | 'off' | undefined | null;
		'data-sveltekit-reload'?: true | '' | 'off' | undefined | null;
		'data-sveltekit-replacestate'?: true | '' | 'off' | undefined | null;
	}
}

export {};


declare module "$app/types" {
	export interface AppTypes {
		RouteId(): "/(entrada)" | "/" | "/(entrada)/forgetpassword" | "/home" | "/(entrada)/signin" | "/(entrada)/signup";
		RouteParams(): {
			
		};
		LayoutParams(): {
			"/(entrada)": Record<string, never>;
			"/": Record<string, never>;
			"/(entrada)/forgetpassword": Record<string, never>;
			"/home": Record<string, never>;
			"/(entrada)/signin": Record<string, never>;
			"/(entrada)/signup": Record<string, never>
		};
		Pathname(): "/" | "/forgetpassword" | "/forgetpassword/" | "/home" | "/home/" | "/signin" | "/signin/" | "/signup" | "/signup/";
		ResolvedPathname(): `${"" | `/${string}`}${ReturnType<AppTypes['Pathname']>}`;
		Asset(): "/robots.txt" | string & {};
	}
}