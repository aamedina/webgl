(ns webgl.core
  (:refer-clojure :exclude [compile flush])
  (:require [clojure-gl.compiler :refer [compile-ns]]            
            [clojure.tools.logging :as log]
            [cljs.analyzer :as ana])
  (:import cljs.tagged_literals.JSValue))

(defonce cljs-files #{})

(defmacro compile
  [shader-ns]
  (when-not (contains? cljs-files ana/*cljs-file*)
    (alter-var-root #'cljs-files conj ana/*cljs-file*))
  (compile-ns shader-ns))

(defmacro context-attributes
  []
  `(.getContextAttributes *gl*))

(defmacro context-lost?
  []
  `(.isContextLost *gl*))

(defmacro supported-extensions
  []
  `(.getSupportedExtensions *gl*))

(defmacro extension
  [name]
  `(.getExtension *gl* ~name))

(defmacro active-texture
  [texture]
  `(.activeTexture *gl* ~texture))

(defmacro attach-shader
  [program shader]
  `(.attachShader *gl* ~program ~shader))

(defmacro bind-attrib-location
  [program index name]
  `(.bindAttribLocation *gl* ~program ~index ~name))

(defmacro bind-buffer
  [target buffer]
  `(.bindBuffer *gl* ~target ~buffer))

(defmacro bind-framebuffer
  [target buffer]
  `(.bindFramebuffer *gl* ~target ~buffer))

(defmacro bind-renderbuffer
  [target buffer]
  `(.bindRenderbuffer *gl* ~target ~buffer))

(defmacro bind-texture
  [target texture]
  `(.bindTexture *gl* ~target ~texture))

(defmacro blend-color
  [r g b a]
  `(.blendColor *gl* ~r ~g ~b ~a))

(defmacro blend-equation
  [mode]
  `(.blendEquation *gl* ~mode))

(defmacro blend-equation-separate
  [mode-rgb mode-alpha]
  `(.blendEquationSeparate *gl* ~mode-rgb ~mode-alpha))

(defmacro blend-func
  [sfactor dfactor]
  `(.blendFunc *gl* ~sfactor ~dfactor))

(defmacro blend-func-separate
  [src-rgb dst-rgb src-alpha dst-alpha]
  `(.blendFuncSeparate *gl* ~src-rgb ~dst-rgb ~src-alpha ~dst-alpha))

(defmacro buffer-data
  [target data usage]
  `(.bufferData *gl* ~target ~data ~usage))

(defmacro buffer-sub-data
  [target offset data]
  `(.bufferSubData *gl* ~target ~offset ~data))

(defmacro check-framebuffer-status
  [target]
  `(.checkFramebufferStatus *gl* ~target))

(defmacro clear
  [mask]
  `(.clear *gl* ~mask))

(defmacro clear-color
  [r g b a]
  `(.clearColor *gl* ~r ~g ~b ~a))

(defmacro clear-depth
  [depth]
  `(.clearDepth *gl* ~depth))

(defmacro clear-stencil
  [s]
  `(.clearStencil *gl* ~s))

(defmacro color-mask
  [r g b a]
  `(.colorMask *gl* ~r ~g ~b ~a))

(defmacro compressed-tex-image-2d
  [target level internalformat width height border data]
  `(.compressedTexImage2D *gl* ~target ~level ~internalformat ~width ~height
                          ~border ~data))

(defmacro compressed-tex-sub-image-2d
  [target level xoffset yoffset width height border]
  `(.compressedTexSubImage2D *gl* ~target ~level ~xoffset ~yoffset
                             ~width ~height ~border))


(defmacro copy-tex-image-2d
  [target level internalformat x y width height border]
  `(.copyTexImage2D *gl* ~target ~level ~internalformat ~x ~y ~width ~height
                    ~border))

(defmacro copy-tex-sub-image-2d
  [target level xoffset yoffset x y width height border]
  `(.copyTexSubImage2D *gl* ~target ~level ~xoffset ~yoffset ~x ~y ~width
                       ~height))

(defmacro create-buffer
  []
  `(.createBuffer *gl*))

(defmacro create-framebuffer
  []
  `(.createFramebuffer *gl*))

(defmacro create-program
  []
  `(.createProgram *gl*))

(defmacro create-renderbuffer
  []
  `(.createRenderbuff *gl*))

(defmacro create-shader
  [type]
  `(.createShader *gl* ~type))

(defmacro create-texture
  []
  `(.createTexture *gl*))

(defmacro cull-face
  [mode]
  `(.cullFace *gl* ~mode))

(defmacro delete-buffer
  [buffer]
  `(.deleteBuffer *gl* ~buffer))

(defmacro delete-framebuffer
  [framebuffer]
  `(.deleteFramebuffer *gl* ~framebuffer))

(defmacro delete-program
  [program]
  `(.deleteProgram *gl* ~program))

(defmacro delete-renderbuffer
  [renderbuffer]
  `(.deleteRenderbuffer *gl* ~renderbuffer))

(defmacro delete-shader
  [shader]
  `(.deleteShader *gl* ~shader))

(defmacro delete-shader
  [texture]
  `(.deleteTexture *gl* ~texture))

(defmacro depth-func
  [func]
  `(.depthFunc *gl* ~func))

(defmacro depth-mask
  [flag]
  `(.depthMask *gl* ~flag))

(defmacro depth-range
  [z-near z-far]
  `(.depthRange *gl* ~z-near ~z-far))

(defmacro detach-shader
  [program shader]
  `(.detachShader *gl* ~program ~shader))

(defmacro disable
  [cap]
  `(.disable *gl* ~cap))

(defmacro disable-vertex-attrib-array
  [index]
  `(.disableVertexAttribArray *gl* ~index))

(defmacro draw-arrays
  [mode first count]
  `(.drawArrays *gl* ~mode ~first ~count))

(defmacro draw-elements
  [mode count type offset]
  `(.drawElements *gl* ~mode ~count ~type ~offset))

(defmacro enable
  [cap]
  `(.enable *gl* ~cap))

(defmacro enable-vertex-attrib-array
  [index]
  `(.enableVertexAttribArray *gl* ~index))

(defmacro finish
  []
  `(.finish *gl*))

(defmacro flush
  []
  `(.flush *gl*))

(defmacro framebuffer-renderbuffer
  [target attachment renderbuffertarget renderbuffer]
  `(.framebufferRenderbuffer *gl* ~target ~attachment ~renderbuffertarget
                             ~renderbuffer))

(defmacro framebuffer-texture-2d
  [target attachment textarget texture level]
  `(.framebufferTexture2D *gl* ~target ~attachment ~textarget ~texture ~level))

(defmacro front-face
  [mode]
  `(.frontFace *gl* ~mode))

(defmacro generate-mipmap
  [target]
  `(.generateMipmap *gl* ~target))

(defmacro active-attrib
  [program index]
  `(.getActiveAttrib *gl* ~program ~index))

(defmacro active-uniform
  [program index]
  `(.getActiveUniform *gl* ~program ~index))

(defmacro attached-shaders
  [program]
  `(.getAttachedShaders *gl* ~program))

(defmacro attrib-location
  [program name]
  `(.getAttribLocation *gl* ~program ~name))

(defmacro uniform-location
  [program name]
  `(.getUniformLocation *gl* ~program ~name))

(defmacro uniform
  [program location]
  `(.getUniform *gl* ~program ~location))

(defmacro buffer-parameter
  [target pname]
  `(.getBufferParameter *gl* ~target ~pname))

(defmacro parameter
  [pname]
  `(.getParameter *gl* ~pname))

(defmacro error
  []
  `(.getError *gl*))

(defmacro use-program
  [program]
  `(.useProgram *gl* ~program))

(defmacro link-program
  [program]
  `(.linkProgram *gl* ~program))

(defmacro program-parameter
  [program pname]
  `(.getProgramParameter *gl* ~program ~pname))

(defmacro shader-parameter
  [shader pname]
  `(.getShaderParameter *gl* ~shader ~pname))

(defmacro shader-source
  [shader source]
  `(.shaderSource *gl* ~shader ~source))

(defmacro compile-shader
  [shader]
  `(let [shader# ~shader
         _# (.compileShader *gl* shader#)
         status# (shader-parameter shader# goog.webgl/COMPILE_STATUS)]
         (if (pos? status#)
           shader#
           (do (js/console.log (.getShaderInfoLog *gl* shader#))
               (delete-shader shader#)))))

(defmacro program
  [vertex fragment]
  `(let [vertex# (doto (create-shader goog.webgl/VERTEX_SHADER)
                   (shader-source (compile ~vertex))
                   (compile-shader))
         fragment# (doto (create-shader goog.webgl/FRAGMENT_SHADER)
                     (shader-source (compile ~fragment))
                     (compile-shader))
         program# (create-program)]
     (doto program#
       (attach-shader vertex#)
       (attach-shader fragment#)
       (link-program))     
     (let [link-status# (program-parameter program# goog.webgl/LINK_STATUS)]
       (if (pos? link-status#)
         program#
         (do (js/console.log (.getProgramInfoLog program#))
             (delete-program program#)
             (delete-shader fragment#)
             (delete-shader vertex#))))))

(defmacro init-shaders
  [vertex fragment]
  `(let [program# (program ~vertex ~fragment)]
     (set! (.-program *gl*) program#)
     (use-program program#)
     program#))

(defmacro vertex-attrib-1f
  [attrib x]
  `(.vertexAttrib1f *gl* ~attrib ~x))

(defmacro vertex-attrib-2f
  [attrib x y]
  `(.vertexAttrib2f *gl* ~attrib ~x ~y))

(defmacro vertex-attrib-3f
  [attrib x y z]
  `(.vertexAttrib3f *gl* ~attrib ~x ~y ~z))

(defmacro vertex-attrib-4f
  [attrib x y z w]
  `(.vertexAttrib4f *gl* ~attrib ~x ~y ~z ~w))

(defmacro vertex-attrib-1fv
  [attrib v]
  `(.vertexAttrib1fv *gl* ~attrib ~v))

(defmacro vertex-attrib-2fv
  [attrib v]
  `(.vertexAttrib2fv *gl* ~attrib ~v))

(defmacro vertex-attrib-3fv
  [attrib v]
  `(.vertexAttrib3fv *gl* ~attrib ~v))

(defmacro vertex-attrib-4fv
  [attrib v]
  `(.vertexAttrib4fv *gl* ~attrib ~v))

(defmacro uniform-1i
  [attrib x]
  `(.uniform1i *gl* ~attrib ~x))

(defmacro uniform-2i
  [attrib x y]
  `(.uniform2i *gl* ~attrib ~x ~y))

(defmacro uniform-3i
  [attrib x y z]
  `(.uniform3i *gl* ~attrib ~x ~y ~z))

(defmacro uniform-4i
  [attrib x y z w]
  `(.uniform4i *gl* ~attrib ~x ~y ~z ~w))

(defmacro uniform-1fv
  [attrib v]
  `(.uniform1fv *gl* ~attrib ~v))

(defmacro uniform-2fv
  [attrib v]
  `(.uniform1fv *gl* ~attrib ~v))

(defmacro uniform-3fv
  [attrib v]
  `(.uniform3fv *gl* ~attrib ~v))

(defmacro uniform-4fv
  [attrib v]
  `(.uniform4fv *gl* ~attrib ~v))

(defmacro uniform-1iv
  [attrib v]
  `(.uniform1iv *gl* ~attrib ~v))

(defmacro uniform-2iv
  [attrib v]
  `(.uniform1iv *gl* ~attrib ~v))

(defmacro uniform-3iv
  [attrib v]
  `(.uniform3iv *gl* ~attrib ~v))

(defmacro uniform-4iv
  [attrib v]
  `(.uniform4iv *gl* ~attrib ~v))

(defmacro uniform-mat2
  [attrib transpose v]
  `(.uniformMatrix2fv *gl* ~attrib ~transpose ~v))

(defmacro uniform-mat3
  [attrib transpose v]
  `(.uniformMatrix3fv *gl* ~attrib ~transpose ~v))

(defmacro uniform-mat4
  [attrib transpose v]
  `(.uniformMatrix4fv *gl* ~attrib ~transpose ~v))

(defmacro vec2
  [& [x y :as vec]]
  `(js/Float32Array. ~(JSValue. vec)))

(defmacro vec3
  [& [x y z :as vec]]
  `(js/Float32Array. ~(JSValue. vec)))

(defmacro vec4
  [& [x y z w :as vec]]
  `(js/Float32Array. ~(JSValue. vec)))

(defmacro mat2
  [& xs]
  (let [v (vec xs)]
    `(js/Float32Array. ~(JSValue. (into [] (for [n (range 4)]
                                             (or (get v n) 0.0)))))))

(defmacro mat3
  [& xs]
  (let [v (vec xs)]
    `(js/Float32Array. ~(JSValue. (into [] (for [n (range 9)]
                                             (or (get v n) 0.0)))))))

(defmacro mat4
  [& xs]
  (let [v (vec xs)]
    `(js/Float32Array. ~(JSValue. (into [] (for [n (range 16)]
                                             (or (get v n) 0.0)))))))


