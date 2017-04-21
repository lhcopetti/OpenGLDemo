#version 330


uniform mat4 uWorld;

in vec3 aPosition;
in vec3 aColor;

out vec4 vColor;

void main() 
{
	gl_Position = vec4(aPosition, 1.0) * uWorld;
	vColor = vec4(aColor, 1.0);
}
