package com.jogamp.opengl.test.junit.jogl.glsl;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL3;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

public class Test_DoubleQuotesInclude_1283 {

	public static void main(final String args[]) throws IOException {
		org.junit.runner.JUnitCore.main(Test_DoubleQuotesInclude_1283.class.getName());
	}

	@Test
	public void shader_DoubleQuotesInclude() throws InterruptedException {

		long ms = 500;

		if (!GLProfile.isAvailable(GLProfile.GL4))
			return;

		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL4));

		GLWindow glWindow = GLWindow.create(caps);
		Assert.assertNotNull(glWindow);
		glWindow.setSize(800, 600);
		glWindow.setVisible(true);
		glWindow.setTitle("JOGL Shader double quotes #include Test");
		Assert.assertTrue(glWindow.isNativeValid());

		glWindow.addGLEventListener(new GLEventListener() {

			@Override
			public void init(GLAutoDrawable drawable) {

				GL3 gl = drawable.getGL().getGL3();

				String shader = "double-quotes";

				ShaderCode vertex = ShaderCode.create(gl, GL_VERTEX_SHADER, getClass(), "shaders", "shader/bin", shader,
						true);
				ShaderCode fragment = ShaderCode.create(gl, GL_FRAGMENT_SHADER, getClass(), "shaders", "shader/bin",
						shader, true);

				ShaderProgram program = new ShaderProgram();
				program.add(gl, vertex, System.err);
//				program.add(gl, fragment, System.err);
//				if (!program.link(gl, System.err))
//					throw new GLException("Couldn't link program: " + program);
			}

			@Override
			public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
			}

			@Override
			public void display(GLAutoDrawable drawable) {
			}

			@Override
			public void dispose(GLAutoDrawable drawable) {
			}
		});

		final Animator animator = new Animator(glWindow);
		animator.start();

		animator.setUpdateFPSFrames(60, System.err);

		while (animator.isAnimating() && animator.getTotalFPSDuration() < ms)
			Thread.sleep(100);

		animator.stop();
		glWindow.destroy();
	}
}
