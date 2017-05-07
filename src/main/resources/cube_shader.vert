#version 330

uniform mat4 uProjection;
uniform mat4 uView;
uniform mat4 uWorld;
uniform mat4 uWorld2;
uniform bool uEnableCamera;

in vec3 aPosition;
in vec3 aColor;

out vec4 vColor;

void main() 
{
	mat4 camera = uProjection * uView;

	if (uEnableCamera)
		gl_Position = uProjection * uView * uWorld * vec4(aPosition, 1.0);
	else
		gl_Position = uWorld * vec4(aPosition, 1.0);
	vColor = vec4(aColor, 1.0);
}
