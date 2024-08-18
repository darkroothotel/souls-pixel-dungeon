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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.BufferUtils;
import com.watabou.glscripts.Script;
import com.watabou.glwrap.Attribute;
import com.watabou.glwrap.Quad;
import com.watabou.glwrap.Uniform;
import com.watabou.glwrap.Vertexbuffer;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class NoosaScript extends Script {
	
	public Uniform uCamera;
	public Uniform uModel;
	public Uniform uTex;
	public Uniform uColorM;
	public Uniform uColorA;
	public Attribute aXY;
	public Attribute aUV;
	public Uniform uTime;
	
	private Camera lastCamera;
	private int shaderId;
	
	public NoosaScript() {

		super();
		//0 is the id of the default shader
		shaderId = Game.getShaderId();
		compile( shader(shaderId) );
		
		uCamera	= uniform( "uCamera" );
		uModel	= uniform( "uModel" );
		uTex	= uniform( "uTex" );
		uColorM	= uniform( "uColorM" );
		uColorA	= uniform( "uColorA" );
		aXY		= attribute( "aXYZW" );
		aUV		= attribute( "aUV" );
		uTime	= uniform( "uTime" );

		Quad.setupIndices();
		Quad.bindIndices();
		
	}

	private void recompileWithShader(){
		shaderId = Game.getShaderId();
		compile( shader(shaderId) );

		uCamera	= uniform( "uCamera" );
		uModel	= uniform( "uModel" );
		uTex	= uniform( "uTex" );
		uColorM	= uniform( "uColorM" );
		uColorA	= uniform( "uColorA" );
		aXY		= attribute( "aXYZW" );
		aUV		= attribute( "aUV" );
		uTime	= uniform( "uTime" );

		Quad.setupIndices();
		Quad.bindIndices();
	}
	
	@Override
	public void use() {
		if(Game.getShaderId() != shaderId){
			IntBuffer shaders = BufferUtils.newIntBuffer(1);

			// Detach the vertex shader
			Gdx.gl.glGetAttachedShaders(handle(), 1, null, shaders);
			Gdx.gl.glDetachShader(handle(), shaders.get(0));

			// Detach the fragment shader
			Gdx.gl.glGetAttachedShaders(handle(), 1, null, shaders);
			Gdx.gl.glDetachShader(handle(), shaders.get(0));
			recompileWithShader();
		}
		super.use();

		aXY.enable();
		aUV.enable();
	}

	void setUTime(){
		uTime.value1f(Game.elapsed);
	}

	public void drawElements( FloatBuffer vertices, ShortBuffer indices, int size ) {

		((Buffer)vertices).position( 0 );
		aXY.vertexPointer( 2, 4, vertices );

		((Buffer)vertices).position( 2 );
		aUV.vertexPointer( 2, 4, vertices );

		Quad.releaseIndices();
		Gdx.gl20.glDrawElements( Gdx.gl20.GL_TRIANGLES, size, Gdx.gl20.GL_UNSIGNED_SHORT, indices );
		Quad.bindIndices();
		setUTime();
	}

	public void drawQuad( FloatBuffer vertices ) {

		((Buffer)vertices).position( 0 );
		aXY.vertexPointer( 2, 4, vertices );

		((Buffer)vertices).position( 2 );
		aUV.vertexPointer( 2, 4, vertices );
		
		Gdx.gl20.glDrawElements( Gdx.gl20.GL_TRIANGLES, Quad.SIZE, Gdx.gl20.GL_UNSIGNED_SHORT, 0 );
		setUTime();
	}

	public void drawQuad( Vertexbuffer buffer ) {

		buffer.updateGLData();

		buffer.bind();

		aXY.vertexBuffer( 2, 4, 0 );
		aUV.vertexBuffer( 2, 4, 2 );

		buffer.release();
		
		Gdx.gl20.glDrawElements( Gdx.gl20.GL_TRIANGLES, Quad.SIZE, Gdx.gl20.GL_UNSIGNED_SHORT, 0 );
		setUTime();
	}
	
	public void drawQuadSet( FloatBuffer vertices, int size ) {
		
		if (size == 0) {
			return;
		}

		((Buffer)vertices).position( 0 );
		aXY.vertexPointer( 2, 4, vertices );

		((Buffer)vertices).position( 2 );
		aUV.vertexPointer( 2, 4, vertices );
		
		Gdx.gl20.glDrawElements( Gdx.gl20.GL_TRIANGLES, Quad.SIZE * size, Gdx.gl20.GL_UNSIGNED_SHORT, 0 );
		setUTime();
	}

	public void drawQuadSet( Vertexbuffer buffer, int length, int offset ){

		if (length == 0) {
			return;
		}

		buffer.updateGLData();

		buffer.bind();

		aXY.vertexBuffer( 2, 4, 0 );
		aUV.vertexBuffer( 2, 4, 2 );

		buffer.release();
		
		Gdx.gl20.glDrawElements( Gdx.gl20.GL_TRIANGLES, Quad.SIZE * length, Gdx.gl20.GL_UNSIGNED_SHORT, Quad.SIZE * Short.SIZE/8 * offset );
		setUTime();
	}
	
	public void lighting( float rm, float gm, float bm, float am, float ra, float ga, float ba, float aa ) {
		uColorM.value4f( rm, gm, bm, am );
		uColorA.value4f( ra, ga, ba, aa );
		setUTime();
	}
	
	public void resetCamera() {
		lastCamera = null;
	}
	
	public void camera( Camera camera ) {
		if (camera == null) {
			camera = Camera.main;
		}
		if (camera != lastCamera && camera.matrix != null) {
			lastCamera = camera;
			uCamera.valueM4( camera.matrix );

			if (!camera.fullScreen) {
				Gdx.gl20.glEnable( Gdx.gl20.GL_SCISSOR_TEST );

				//This fixes pixel scaling issues on some hidpi displays (mainly on macOS)
				// because for some reason all other openGL operations work on virtual pixels
				// but glScissor operations work on real pixels
				float xScale = (Gdx.graphics.getBackBufferWidth() / (float)Game.width );
				float yScale = ((Gdx.graphics.getBackBufferHeight()-Game.bottomInset) / (float)Game.height );

				Gdx.gl20.glScissor(
						Math.round(camera.x * xScale),
						Math.round((Game.height - camera.screenHeight - camera.y) * yScale) + Game.bottomInset,
						Math.round(camera.screenWidth * xScale),
						Math.round(camera.screenHeight * yScale));
			} else {
				Gdx.gl20.glDisable( Gdx.gl20.GL_SCISSOR_TEST );
			}
		}
		setUTime();
	}
	
	public static NoosaScript get() {
		return Script.use( NoosaScript.class );
	}
	
	
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
		"uniform vec4 uColorM;\n" +
		"uniform vec4 uColorA;\n" +
		"void main() {\n" +
		"  gl_FragColor = texture2D( uTex, vUV ) * uColorM + uColorA;\n" +
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
