#version 330

uniform mat4 uWorld;

in vec2 aPosition;
in vec3 aColor;

void main() 
{
//	gl_Position = vec4(aPosition, 0.0, 1.0) * uWorld;
	gl_Position = (vec4(aPosition, 0.0, 1.0) + vec4(0.3, 0.3, 0.0, 0.0)) * uWorld;
}
