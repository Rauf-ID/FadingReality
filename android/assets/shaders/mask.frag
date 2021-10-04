// no define stuff, we dont care about that for testing
varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
  gl_FragColor = texture2D(u_texture, v_texCoords) * vec4(1.0, 1.0, 1.0, 0.1);
}