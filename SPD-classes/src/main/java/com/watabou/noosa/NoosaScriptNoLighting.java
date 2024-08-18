/*
 *
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
 *
 * Souls Pixel Dungeon
 * Copyright (C) 2024 Bartolomeo Cold
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.watabou.noosa;

import com.watabou.glscripts.Script;

//This class should be used on heavy pixel-fill based loads when lighting is not needed.
// It skips the lighting component of the fragment shader, giving a significant performance boost

//Remember that switching programs is expensive
// if this script is to be used many times try to block them together
public class NoosaScriptNoLighting extends NoosaScript {

	@Override
	public void lighting(float rm, float gm, float bm, float am, float ra, float ga, float ba, float aa) {
		//Does nothing
		setUTime();
	}

	public static NoosaScriptNoLighting get(){
		return Script.use( NoosaScriptNoLighting.class );
	}

	@Override
	protected String shader(int id) {
		switch (id){
			case 0: default:
				return SHADER;
			case 1:
				return SHADER1;
		}
	}

	private static final String SHADER =
		
		//vertex shader
		"uniform mat4 uCamera;\n" +
		"uniform mat4 uModel;\n" +
		"attribute vec4 aXYZW;\n" +
		"attribute vec2 aUV;\n" +
		"varying vec2 vUV;\n" +
		"void main() {\n" +
		"  gl_Position = uCamera * uModel * aXYZW;\n" +
		"  vUV = aUV;\n" +
		"}\n" +
		
		//this symbol separates the vertex and fragment shaders (see Script.compile)
		"//\n" +
		
		//fragment shader
		//preprocessor directives let us define precision on GLES platforms, and ignore it elsewhere
		"#ifdef GL_ES\n" +
		"  precision mediump float;\n" +
		"#endif\n" +
		"varying vec2 vUV;\n" +
		"uniform sampler2D uTex;\n" +
		"void main() {\n" +
		"  gl_FragColor = texture2D( uTex, vUV );\n" +
		"}\n";

	private static final String SHADER1 =

			//vertex shader
			"uniform mat4 uCamera;\n" +
					"uniform mat4 uModel;\n" +
					"attribute vec4 aXYZW;\n" +
					"attribute vec2 aUV;\n" +
					"varying vec2 vUV;\n" +
					"void main() {\n" +
					"  gl_Position = uCamera * uModel * aXYZW;\n" +
					"  vUV = aUV;\n" +
					"}\n" +

					//this symbol separates the vertex and fragment shaders (see Script.compile)
					"//\n" +

					//fragment shader
					//preprocessor directives let us define precision on GLES platforms, and ignore it elsewhere
					"#ifdef GL_ES\n" +
					"  precision mediump float;\n" +
					"#endif\n" +
					"\n" +
					"varying vec2 vUV;\n" +
					"uniform sampler2D uTex;\n" +
					"uniform vec4 uColorM;  // Base color with metallic properties\n" +
					"uniform vec4 uColorA;  // Ambient color\n" +
					"uniform float uTime;   // Time uniform for animating the shimmer\n" +
					"\n" +
					"void main() {\n" +
					"  // Sample the base texture color\n" +
					"  vec4 texColor = texture2D(uTex, vUV);\n" +
					"\n" +
					"  // Check if the pixel is fully transparent\n" +
					"  if (texColor.a < 1.0) {\n" +
					"    // If fully transparent, discard the shimmer effect\n" +
					"    gl_FragColor = texColor;\n" +
					"  } else {\n" +
					"    // Create a shimmer effect based on time\n" +
					"    float shimmer = sin(uTime * 5.0) * 0.5 + 0.5;\n" +
					"\n" +
					"    // Boost the metallic color by adding the shimmer effect\n" +
					"    vec4 metallicColor = mix(texColor * uColorM, vec4(1.0, 1.0, 1.0, 1.0), shimmer);\n" +
					"\n" +
					"    // Combine the metallic color with ambient color\n" +
					"    gl_FragColor = metallicColor + uColorA;\n" +
					"  }\n" +
					"}\n";
}
