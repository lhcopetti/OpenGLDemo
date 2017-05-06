#version 330

uniform float uTime;
uniform mat4 uWorld;

in vec2 aPosition;

void main()
{
	float distanceToOrigin = sqrt(pow(aPosition.x, 2) + pow(aPosition.y, 2));
	float sinWave = sin(distanceToOrigin + 3 * uTime);
	gl_Position = vec4(aPosition, sinWave, 1.0) * uWorld;
}
