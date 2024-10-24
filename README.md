# Proyecto: Planeación de gestión de datos de nanotecnologia. 

## Contexto
En el campo de la nanotecnología, los científicos realizan experimentos sobre materiales que operan a nano escala, estos generan datos relacionados con las propiedades físicas y químicas de los materiales estudiados.

Estos datos son importantes para comprender y predecir cómo los materiales se comportan al aplicar ciertas fuerzas o condiciones, como cambios de temperatura o presión. Para poder usarlos y poder realizar predicciones precisas, es necesario el construir una estructura de datos que permita el acceso de esa información de forma optimizada.

[![images.jpg](https://i.postimg.cc/1zY0ddD1/images.jpg)](https://postimg.cc/McVjMds9)

## Objetivo
El objetivo principal de este proyecto es desarrollar una plataforma de gestión de datos que facilite a los científicos acceder y analizar estos datos, impulsando así nuevas predicciones sobre el comportamiento de los materiales bajo distintas condiciones.  

## Datos

Para obtener los datos que se usaran en este proyecto se empleo la herramienta de Mockaroo. Generando 3 tablas (Materiales, propiedades, condiciones). 

La primera tabla llamada materiales incluirá campos como; un “id” (“id_material”) (integer) que será la llave principal, “nombre” (text), “composicion_quimica” (text), “aplicacion” (text), y “fecha_creacion” (date). 

Tendremos una segunda tabla llamada “condiciones”, la cual estará compuesta de un “id” (“id_experimento”) que será la llave principal (integer), “id_material” que será la llave foránea con la tabla “material” (integer), “temperatura” (integer), “presión_aplicada” (numeric), “duración_experimento” (integer), “fecha_experimento” (date).   

La tercera tabla llamada “propiedades”, estará compuesta por id (“id_propiedad”) que será la llave principal (integer), “id_material” que será la llave foránea (integer), “resistencia” (integer), “elasticidad” (integer), “conductividad_termica” (numeric), “densidad” (numeric), “diámetro_nanoparticulas” (integer), “espesor_capa” (integer), “capacidad_absorcion_luz” (integer).  

![EBD](Data/EBD.png)

## Flujo de trabajo. 

![Flujodedatos](https://github.com/JaimeMoc/Plataforma_de_gestion_de_datos_de_Nanotecnologia/blob/e72407033b7995a9ffbbbf5c1bb187355d5025bd/Diagrama%20.png)

# Ingesta de Datos (Data Ingestion):

- **Airflow (DAG)** orquesta el proceso de ingesta de datos.
- **Databricks** es responsable de ingerir y procesar los datos en bruto de archivos como `Materiales.csv`, `Propiedades.csv` y `Condiciones.csv`.

# Transformación de Datos (Data Transformation):

- **Airflow (DAG)** gestiona las tareas de transformación.
- **Databricks** maneja las transformaciones de datos reales, almacenando los resultados en **Delta Lake** con diferentes niveles:
  - **Bronze**: Datos en bruto.
  - **Silver**: Datos limpiados y enriquecidos.
  - **Gold**: Datos completamente procesados y listos para el análisis.

# Validación y Calidad (Validation and Quality):

- **Airflow (DAG)** asegura la calidad de los datos ejecutando tareas de validación.

# Visualización (Visualization):

- **Power BI** genera informes y paneles interactivos a partir de los datos procesados.

# Canalización de ML (ML Pipeline):

- **Airflow (DAG)** orquesta la canalización de aprendizaje automático.
- **MLFlow** se utiliza para gestionar el ciclo de vida de los modelos de aprendizaje automático.

# Despliegue de Modelos (Model Deployment):

- **Azure Machine Learning Server** es responsable de desplegar los modelos entrenados.

# Gobernanza de Datos (Data Governance):

- **Airflow** gestiona el linaje de datos, la auditoría y la validación para asegurar la gobernanza a lo largo de la canalización.
