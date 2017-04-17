#version 330

uniform mat4 uWorld;

in vec3 aPosition;

void main()
{
	gl_Position = vec4(aPosition, 1.0) * uWorld;
};
