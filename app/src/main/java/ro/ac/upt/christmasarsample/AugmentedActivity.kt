package ro.ac.upt.christmasarsample

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode


class AugmentedActivity : AppCompatActivity() {

    private lateinit var arFragment: ArFragment

    private var renderable: ModelRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_augmented)

        initRenderableModel()

        arFragment = supportFragmentManager.findFragmentById(R.id.scf_central) as ArFragment

//        TODO("2. Invoke addRenderableToScene once a tap is executed over the AR plane")


        arFragment.setOnTapArPlaneListener { hr, p, me ->

            val anchor = hr.createAnchor()
            addRenderableToScene(anchor = anchor, renderable = renderable!!)

        }
    }

    private fun initRenderableModel() {
        val modelUri = Uri.parse("model.sfb")

//        TODO("1. Init model renderable variable")
        ModelRenderable.builder()
            .setSource(this, modelUri)
            .build()
            .thenAccept { r -> renderable = r }
            .exceptionally { throwable ->
                Log.e(TAG, "Unable to load Renderable.", throwable)
                null
            }

    }

    private fun addRenderableToScene(anchor: Anchor, renderable: Renderable) {
//        TODO("3. Build an anchor node and set the AR scene to be its parent")

        val node = AnchorNode(anchor)
        node.setParent(arFragment.arSceneView.scene)


//        TODO("4. Build an transformable node and set the previously anchor node to be its parent")
        val transformableNode = TransformableNode(arFragment.transformationSystem)


//        TODO("5. Assign node's renderable property to previously loaded renderable")
        transformableNode.renderable = renderable
        transformableNode.setParent(node)
        transformableNode.select()
    }



    companion object {
        private val TAG = AugmentedActivity::class.java.simpleName
    }

}
