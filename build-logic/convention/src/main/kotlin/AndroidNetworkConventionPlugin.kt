import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties


class AndroidNetworkConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            pluginManager.apply("convention.android.library")

            val properties = Properties()
            val localPropertiesFile = rootProject.file("local.properties")
            if(localPropertiesFile.exists()){
                properties.load(localPropertiesFile.inputStream())
            }

            val token = properties.getProperty("BEARER_TOKEN") ?: "\"\""
            val baseUrl = properties.getProperty("BASE_URL") ?: "\"\""

            extensions.configure<LibraryExtension>{
                buildFeatures{
                    buildConfig = true
                }

                defaultConfig{
                    buildConfigField("String","BEARER_TOKEN",token)
                    buildConfigField("String","BASE_URL",baseUrl)
                }
            }
        }
    }
}