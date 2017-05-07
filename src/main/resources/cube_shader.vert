#version 330

uniform mat4 uProjection;
uniform mat4 uView;
uniform mat4 uWorld;

uniform vec3 uCameraPosition;
uniform vec3 uLightPos;

in vec3 aPosition;
in vec3 aColor;
in vec3 aNormal;

out vec4 vColor;
out vec3 vNormal;
out vec3 vViewPath;
out vec3 vLightDir;

void main() 
{
	vec4 worldPos = uWorld * vec4(aPosition, 1.0);
	gl_Position = uProjection * uView * worldPos;

	vNormal = (uWorld * vec4(aNormal, 0.0)).xyz;
	vViewPath = uCameraPosition - worldPos.xyz;
	vColor = vec4(aColor, 1.0);
	vLightDir = (worldPos - vec4(uLightPos, 0.0)).xyz;
}
