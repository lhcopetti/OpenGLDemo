#version 330
			
uniform vec3 uAmbientLight;
uniform vec3 uDiffuseLight;

uniform vec3 uSpecularLight;
uniform float uSpecularPower;

out vec4 out_color;

in vec3 vNormal;
in vec3 vViewPath;
in vec4 vColor;
in vec3 vLightDir;

void main() 
{
	vec3 L = normalize(vLightDir);
	vec3 N = normalize(vNormal);
	vec3 V = normalize(vViewPath);

	vec3 ambient = uAmbientLight;

	float diffuseIntensity = max(dot(N, -L), 0.0);
	vec3 diffuse = diffuseIntensity * uDiffuseLight;

	float specularIntensity = 0.0;
	if (uSpecularPower > 0.0) {
		vec3 R = reflect(L, N);
		specularIntensity = pow(max(dot(R, V), 0.0), uSpecularPower);
	}
	vec3 specular = specularIntensity * uSpecularLight;

	vec3 color = clamp(ambient + diffuse + specular, 0.0, 1.0);
	out_color = vec4(color, 1.f);
}
