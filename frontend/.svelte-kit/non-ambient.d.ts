
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
		RouteId(): "/(in)" | "/(entrada)" | "/" | "/(in)/conta" | "/(in)/favoritos" | "/(entrada)/forgetpassword" | "/(in)/home" | "/(in)/investimentos" | "/(in)/modulos" | "/(in)/perfil" | "/(entrada)/signin" | "/(entrada)/signup";
		RouteParams(): {
			
		};
		LayoutParams(): {
			"/(in)": Record<string, never>;
			"/(entrada)": Record<string, never>;
			"/": Record<string, never>;
			"/(in)/conta": Record<string, never>;
			"/(in)/favoritos": Record<string, never>;
			"/(entrada)/forgetpassword": Record<string, never>;
			"/(in)/home": Record<string, never>;
			"/(in)/investimentos": Record<string, never>;
			"/(in)/modulos": Record<string, never>;
			"/(in)/perfil": Record<string, never>;
			"/(entrada)/signin": Record<string, never>;
			"/(entrada)/signup": Record<string, never>
		};
		Pathname(): "/" | "/conta" | "/conta/" | "/favoritos" | "/favoritos/" | "/forgetpassword" | "/forgetpassword/" | "/home" | "/home/" | "/investimentos" | "/investimentos/" | "/modulos" | "/modulos/" | "/perfil" | "/perfil/" | "/signin" | "/signin/" | "/signup" | "/signup/";
		ResolvedPathname(): `${"" | `/${string}`}${ReturnType<AppTypes['Pathname']>}`;
		Asset(): "/robots.txt" | string & {};
	}
}