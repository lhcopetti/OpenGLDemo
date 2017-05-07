#version 330
			
uniform vec3 uAmbientLight;
uniform vec3 uAmbientMaterial;

uniform vec3 uDiffuseLight;
uniform vec3 uDiffuseMaterial;

uniform vec3 uSpecularLight;
uniform vec3 uSpecularMaterial;
uniform float uSpecularPowerMaterial;

uniform float uAttenuationFactor;

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

	vec3 ambient = uAmbientLight * uAmbientMaterial;

	float diffuseIntensity = max(dot(N, -L), 0.0);
	vec3 diffuse = diffuseIntensity * uDiffuseLight * uDiffuseMaterial;

	float specularIntensity = 0.0;
	if (uSpecularPowerMaterial > 0.0) {
		vec3 R = reflect(L, N);
		specularIntensity = pow(max(dot(R, V), 0.0), uSpecularPowerMaterial);
	}
	vec3 specular = specularIntensity * uSpecularLight * uSpecularMaterial;

	float lightDistance = length(vLightDir);
	float attenuationValue = 1 / pow(lightDistance * uAttenuationFactor, 2);
	float attenuation = clamp(attenuationValue, 0.0, 1.0);

	vec3 color = clamp(ambient + diffuse + specular, 0.0, 1.0);
	out_color = vec4(color * attenuation, 1.f);
}
