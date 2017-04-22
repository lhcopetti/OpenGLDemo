#version 330

uniform mat4 uWorld;
uniform float uTime;

in vec2 aPosition;

void main()
{
	float distanceToOrigin = sqrt(pow(aPosition.x, 2) + pow(aPosition.y, 2));
	gl_Position = vec4(aPosition, sin(distanceToOrigin + uTime), 1.0) * uWorld;
}
