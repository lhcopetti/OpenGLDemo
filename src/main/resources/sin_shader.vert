#version 330

uniform float uTime;
uniform mat4 uWorld;

in vec2 aPosition;

void main()
{
	float distanceToOrigin = sqrt(pow(aPosition.x, 2) + pow(aPosition.y, 2));
	float anotherTime = uTime;
	float sinWave = sin(distanceToOrigin);

//	gl_Position = vec4(aPosition, sin(distanceToOrigin + uTime), 1.0) * uWorld;
	gl_Position = vec4(aPosition, sinWave, 1.0) * uWorld;
}
