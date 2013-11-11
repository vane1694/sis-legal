
spool 00_MODIFICACIONES_GESLEG.log

prompt =====================================================
prompt Modificando la tabla GESLEG.PERSONA
prompt =====================================================

ALTER TABLE GESLEG.PERSONA DROP COLUMN ESTADO;
ALTER TABLE GESLEG.ORGANO DROP COLUMN ESTADO;
ALTER TABLE GESLEG.ABOGADO DROP COLUMN ESTADO;
COMMIT;



ALTER TABLE GESLEG.PERSONA ADD ESTADO CHAR(1) DEFAULT 'A' NOT NULL;
ALTER TABLE GESLEG.ORGANO ADD ESTADO CHAR(1) DEFAULT 'A' NOT NULL;
ALTER TABLE GESLEG.ABOGADO ADD ESTADO CHAR(1) DEFAULT 'A' NOT NULL;

prompt =====================================================
prompt Creando o modificando el procedimiento "SP_ETL_DETALLADO"
prompt Tipo de reporte
prompt =====================================================

create or replace
PROCEDURE          "SP_ETL_DETALLADO" ( 
proceso        IN NUMBER,                                                                                            
via            IN NUMBER,                                                                                           
instancia      IN NUMBER,                                                                                          
responsable    IN VARCHAR2,                                                                                          
fechaInicio    IN DATE,                                                                                            
fechaFin       IN DATE,                                                                                           
banca          IN NUMBER,                                                                                            
territorio     IN NUMBER,                                                                                         
oficina        IN VARCHAR2,                                                                                         
p_departamento IN VARCHAR2,                                                                                          
p_provincia    IN VARCHAR2,                                                                                          
p_distrito     IN VARCHAR2,                                                                                         
tipoExpediente IN NUMBER,                                                                                            
calificacion   IN NUMBER,                                                                                           
organo         IN VARCHAR2,                                                                                          
recurrencia    IN VARCHAR2,                                                                                          
riesgo         IN NUMBER,                                                                                            
actProcesal    IN NUMBER,                                                                                            
materia        IN VARCHAR2,                                                                                          
p_estado       IN NUMBER,                                                                                            
tipoImporte    IN VARCHAR2,
moneda         IN NUMBER,
importeMinimo  IN NUMBER, 
importeMaximo  IN NUMBER,
p_nombre       IN VARCHAR2,
rol            IN NUMBER,                                                                                            
p_estudio      IN NUMBER,                                                                                            
abogado        IN VARCHAR2,
tipoUbigeo in number
)
IS
BEGIN
     execute immediate 'truncate table gesleg.FACT_DETALLADO';

/************************************************ INSERT TOTALES ***********************************************************************/
/***************************************************************************************************************************************/
 insert  INTO GESLEG.FACT_DETALLADO( 
ID_GRUPO_BANCA, GRUPO_BANCA, ID_TERRITORIO, TERRITORIO, ID_OFICINA, OFICINA, ID_PROCESO, PROCESO, 
 ID_VIA, VIA, ID_INSTANCIA, INSTANCIA, ID_RESPONSABLE, RESPONSABLE, NUMERO_EXPEDIENTE, FECHA_INICIO_PROCESO,
 ID_ESTADO_EXPEDIENTE, ESTADO_EXPEDIENTE, ID_CALIFICACION, CALIFICACION, ID_RECURRENCIA, RECURRENCIA, 
 ID_TIPO_EXPEDIENTE, TIPO_EXPEDIENTE, ID_ORGANO, ORGANO, SECRETARIO, ESTUDIOS_EXTERNOS, PERSONAS, MATERIAS,
 TOTAL_SOLES_MATERIA, INCULPADOS, TOTAL_SOLES_CAUCION, TIPOS, TOTAL_SOLES_MEDIDA_CAUTELAR, ID_RIESGO, RIESGO, 
 ULT_ACTIVIDAD_PROCESAL_VIGENTE
 ) 
 select 
nvl(b.id_grupo_banca,0)id_grupo_banca, NVL(b.descripcion, 'SIN BANCA')  GRUPO_BANCA, nvl(t.id_territorio,0)id_territorio, NVL(t.descripcion, 'SIN TERRITORIO') TERRITORIO,
nvl(e.id_oficina,0)id_oficina, e.id_oficina || ' ' || NVL(o.nombre,'SIN OFICINA') OFICINA,  e.id_proceso,P.NOMBRE PROCESO,
e.id_via, nvl(V.NOMBRE,' ') VIA, e.id_instancia, nvl(i.NOMBRE,' ') INSTANCIA,e.id_usuario  id_responsable, U.NOMBRE_COMPLETO RESPONSABLE,
e.numero_expediente, e.fecha_inicio_proceso, e.id_estado_expediente, ex.nombre ESTADO_EXPEDIENTE, e.id_calificacion,
nvl(C.NOMBRE,' ') CALIFICACION, e.id_recurrencia, nvl(REC.NOMBRE,' ') RECURRENCIA,e.id_tipo_expediente,nvl(TP.NOMBRE,' ') TIPO_EXPEDIENTE,
e.id_organo , ORG.NOMBRE ORGANO ,nvl(e.secretario,' ')secretario, nvl(E.ESTUDIOS_EXTERNOS,' ') ESTUDIOS_EXTERNOS, nvl(E.PERSONAS,' ') PERSONAS,
NVL(E.MATERIAS,' ')MATERIAS, NVL(E.TOTAL_SOLES_MATERIA, 0)TOTAL_SOLES_MATERIA,
NVL(E.INCULPADOS,' ')  INCULPADOS, NVL(E.TOTAL_SOLES_CAUCION ,0)TOTAL_SOLES_CAUCION,
NVL(E.TIPOS,' ')  TIPOS, NVL(E.TOTAL_SOLES_MEDIDA_CAUTELAR ,0)TOTAL_SOLES_MEDIDA_CAUTELAR ,
e.id_riesgo, nvl(R.DESCRIPCION,' ') RIESGO,  ULT_ACTIVIDAD_PROCESAL_VIGENTE


from ( 
select 

e.id_oficina,  e.id_proceso,
e.id_via,  e.id_instancia,e.id_usuario  , 
e.numero_expediente, e.fecha_inicio_proceso, e.id_estado_expediente,  e.id_calificacion, e.id_recurrencia, 
e.id_tipo_expediente,
e.id_organo , e.secretario, 
 vista.ABOGADO_list ESTUDIOS_EXTERNOS, vista_personas.PERSONAS_list PERSONAS,
NVL(VISTA_CUANTIA.MATERIA_list,'')MATERIAS, NVL(VISTA_CUANTIA.CALCULADO_SOLES_x,'')TOTAL_SOLES_MATERIA,
NVL(VISTA_CAUCION.INCULPADOS_list,'')  INCULPADOS, NVL(VISTA_CAUCION.CALCULADO_SOLES_x ,'')TOTAL_SOLES_CAUCION,
NVL(VISTA_MEDIDA_CAUTELAR.TIPOS,'')  TIPOS, NVL(VISTA_MEDIDA_CAUTELAR.CALCULADO_SOLES ,'')TOTAL_SOLES_MEDIDA_CAUTELAR ,
e.id_riesgo, NVL(vista_Ultima_Actividad.ULT_ACTIVIDAD_PROCESAL_VIGENTE ,' ') ULT_ACTIVIDAD_PROCESAL_VIGENTE

from 
(select e.id_expediente,
e.id_oficina,  e.id_proceso,
e.id_via,  e.id_instancia,e.id_usuario  , 
e.numero_expediente, e.fecha_inicio_proceso, e.id_estado_expediente,  e.id_calificacion, e.id_recurrencia, 
e.id_tipo_expediente,
e.id_organo , e.secretario, e.id_riesgo,  ' ' ULT_ACTIVIDAD_PROCESAL_VIGENTE
from GESLEG.expediente e

where e.exp_id_expediente is null 

 AND  (fechaInicio IS NULL OR fechaInicio ='' OR  e.fecha_inicio_proceso >=  fechaInicio )
 AND (fechaFin IS NULL OR fechaFin ='' OR   e.fecha_inicio_proceso <= fechaFin )
 AND (responsable IS NULL OR RESPONSABLE=0 OR e.id_usuario=responsable) 
  /** PROCESO VIA INSTANCIA*/
 AND  ( proceso =0 OR e.id_proceso=PROCESO )
 AND  (VIA =0 OR  e.id_via=VIA)
 AND  (INSTANCIA =0 OR  e.id_instancia= instancia     )
 
 AND  (BANCA = 0 OR 
      e.id_oficina in ( select id_oficina from GESLEG.oficina where id_territorio in  ( select id_territorio from GESLEG.territorio
                     where id_grupo_banca = (  select id_grupo_banca 
                                               from GESLEG.grupo_banca g 
                                               where g.id_grupo_banca= BANCA)) ) 
        )
 
 AND  (TERRITORIO = 0 or
      e.id_oficina in ( select id_oficina from GESLEG.oficina where id_territorio =TERRITORIO ) 
        )       
 AND  (OFICINA IS  NULL or e.id_oficina in OFICINA  )      
    
 --DEPARTAMENTO --
  AND (tipoUbigeo=2 AND (p_departamento IS NULL OR e.id_oficina in (select id_oficina from GESLEG.oficina where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_dep =p_departamento) )))
  and (tipoUbigeo=2 AND (p_provincia IS NULL OR e.id_oficina in ( select id_oficina from GESLEG.oficina where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_prov =p_provincia))))
  and (tipoUbigeo=2 and (p_distrito is null OR e.id_oficina in ( select id_oficina from GESLEG.oficina where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_dist =p_distrito) )) )
  
  
  and (tipoUbigeo=1 and (p_distrito is null OR e.id_organo in ( select id_organo from GESLEG.organo where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_dist =p_distrito) )) ) 
  
  and (tipoUbigeo=0 AND (p_departamento IS NULL OR e.id_oficina in (select id_oficina from GESLEG.oficina where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_dep =p_departamento) )))
  and (tipoUbigeo=0 and (p_provincia IS NULL OR e.id_oficina in ( select id_oficina from GESLEG.oficina where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_prov =p_provincia) )))
  and (tipoUbigeo=0 and (p_distrito is null OR e.id_oficina in ( select id_oficina from GESLEG.oficina where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_dist =p_distrito) )) )
  and (tipoUbigeo=0 and (p_distrito is null OR e.id_organo in ( select id_organo from GESLEG.organo where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_dist =p_distrito) )))
          
  AND (tipoExpediente = 0   OR E.ID_TIPO_EXPEDIENTE=tipoExpediente  )
  
  AND (calificacion =0 OR e.id_calificacion=calificacion  ) 
  
  AND (ORGANO IS NULL  OR E.ID_ORGANO=ORGANO  ) 
 
  AND (RECURRENCIA IS NULL OR E.ID_RECURRENCIA=RECURRENCIA  )
  
  AND (RIESGO =0 OR E.id_riesgo=RIESGO  )
  
  AND (actProcesal=0 OR  actProcesal IN ( SELECT ID_ACTIVIDAD  FROM ACTIVIDAD_PROCESAL WHERE ID_EXPEDIENTE=e.id_expediente) )
  
 -- AND (MATERIA IS NULL )
  
  AND (p_estado=0 OR e.id_estado_expediente =p_estado)
  
  
  /** Estudio y Abogado **/
  AND ( P_estudio=0 OR E.ID_EXPEDIENTE IN (SELECT  id_expediente from honorario
                          where id_abogado in ( SELECT id_abogado FROM GESLEG.abogado_estudio where estado = 'A' AND id_estudio=P_estudio)
                                           )  
      )
  AND ( ABOGADO IS NULL OR E.ID_EXPEDIENTE IN (SELECT  id_expediente from honorario
                          where id_abogado in ( SELECT id_abogado FROM GESLEG.abogado_estudio where estado = 'A' AND id_abogado=ABOGADO )
                         )  
      )  
  /** ROL Y PERSONA **/    
  
  AND (ROL =0 OR E.ID_EXPEDIENTE IN ( select id_Expediente from involucrado where id_rol_involucrado = ROL) )
  
  AND (p_nombre IS NULL OR E.ID_EXPEDIENTE IN ( select id_Expediente from GESLEG.involucrado where  id_persona=p_nombre ) )
   
  AND (MATERIA IS null  OR e.id_EXPEDIENTE IN (select id_expediente from GESLEG.CUANTIA where id_materia = MATERIA )  )    


  AND (tipoImporte is null 
  
    /** Importes cuantias **/
   or (tipoImporte=3 AND  e.id_EXPEDIENTE IN (  select id_expediente from (
                                                SELECT  CUTIA.ID_EXPEDIENTE, SUM(CUTIA.PRETENDIDO) MATERIA, mo.id_moneda id_monedax
                                            FROM GESLEG.CUANTIA  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                            INNER JOIN GESLEG.MATERIA M ON cutia.id_materia=m.id_materia 
                                            INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                             WHERE  EXP_ID_EXPEDIENTE IS NULL
                                             GROUP BY CUTIA.ID_EXPEDIENTE, CUTIA.ID_MONEDA,mo.id_moneda, m.descripcion ,m.id_materia 
                                             order by id_expediente asc )
                                             where materia between importeMinimo and importeMaximo   and (moneda = 0 or id_monedax =moneda) 
                                             )
      )
     /** Importes caucion **/ 
   or (tipoImporte=1 AND  e.id_EXPEDIENTE IN (  select id_expediente from (
                                                  SELECT  CUTIA.ID_EXPEDIENTE, CUTIA.id_moneda  , SUM(CUTIA.MONTO)   INCULPADOS
                                              FROM GESLEG.INCULPADO  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                              INNER JOIN GESLEG.PERSONA M ON cutia.id_persona=m.id_persona
                                              INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                              WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                              FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                              GROUP BY CUTIA.ID_EXPEDIENTE,CUTIA.ID_MONEDA order by id_expediente asc     )
                                              where INCULPADOS between importeMinimo and importeMaximo   and (moneda = 0 or id_moneda =moneda) 
                                             )
      )
       /** Importes medida cautelar **/
    or (tipoImporte=2 AND  e.id_EXPEDIENTE IN (  select id_expediente from (
                                                  SELECT  e.ID_EXPEDIENTE, e.id_moneda  , SUM(e.importe_cautelar)TIPOS
                                                  FROM GESLEG.tipo_cautelar  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON  cutia.id_tipo_cautelar=e.id_tipo_cautelar
                                                  INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=e.id_moneda
                                                  WHERE e.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                                  FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                                  GROUP BY  E.ID_EXPEDIENTE, E.ID_MONEDA,cutia.descripcion  order by id_expediente asc )
                                              where TIPOS between importeMinimo and importeMaximo   and (moneda = 0 or id_moneda =moneda) 
                                             )
     )                                        
   ) -- Fin Tipo IMporte
     

   
) e


LEFT join ( 
select id_expediente,listagg(ABOGADO, ' / ')WITHIN GROUP (ORDER BY ABOGADO)ABOGADO_list
FROM (select h.id_expediente,e.nombre||'('|| a.nombre_completo||')' ABOGADO
                                from GESLEG.honorario h inner join   GESLEG.abogado_estudio ae on h.id_abogado=ae.id_abogado
                                inner join GESLEG.estudio e on ae.id_estudio=e.id_estudio 
                                inner join GESLEG.abogado a on a.id_abogado=ae.id_abogado 
                                ) GROUP BY  id_expediente

  /*select  z20.id_expediente, z20.ABOGADO_list
              from ( select  z10.id_expediente
                              ,SUBSTR(SYS_CONNECT_BY_PATH(z10.ABOGADO, ','), 2) ABOGADO_list ,CONNECT_BY_ISLEAF islf
                                from ( select  z.id_expediente,z.ABOGADO,row_number() over(partition by z.id_expediente order by z.ABOGADO) rn
                                from (select h.id_expediente,e.nombre||'('|| a.nombre_completo||')' ABOGADO
                                from GESLEG.honorario h inner join   GESLEG.abogado_estudio ae on h.id_abogado=ae.id_abogado
                                inner join GESLEG.estudio e on ae.id_estudio=e.id_estudio 
                                inner join GESLEG.abogado a on a.id_abogado=ae.id_abogado 
                                )z group by z.id_expediente,z.ABOGADO ) z10 start with z10.rn = 1
                                connect by z10.id_expediente = prior z10.id_expediente and z10.rn = prior z10.rn + 1
                              ) z20 where z20.islf = 1 */) vista
on vista.id_expediente=e.id_expediente
            
LEFT join (
select id_expediente,listagg(PERSONAS, ',')WITHIN GROUP (ORDER BY PERSONAS)PERSONAS_list
FROM (
select id_expediente, r.nombre || ' ' ||p.numero_documento||' ' ||p.nombre_completo PERSONAS
from GESLEG.involucrado i inner join GESLEG.rol_involucrado r ON i.id_rol_involucrado=r.id_rol_involucrado 
INNER JOIN GESLEG.PERSONA P ON p.id_persona=i.id_persona
order by id_expediente  asc 
) GROUP BY  id_expediente 

/* select  z20.id_expediente, z20.PERSONAS_list
             from ( select z10.id_expediente ,SUBSTR(SYS_CONNECT_BY_PATH(z10.PERSONAS, ','), 2) PERSONAS_list ,CONNECT_BY_ISLEAF islf
                                     from (select  z.id_expediente,z.PERSONAS,row_number() over(partition by z.id_expediente order by z.PERSONAS) rn
                                             from ( select id_expediente, r.nombre || ' ' ||p.numero_documento||' ' ||p.nombre_completo PERSONAS
                                             from GESLEG.involucrado i inner join GESLEG.rol_involucrado r ON i.id_rol_involucrado=r.id_rol_involucrado 
                                            INNER JOIN GESLEG.PERSONA P ON p.id_persona=i.id_persona
                                            order by id_expediente  asc
                                              )z group by z.id_expediente,z.PERSONAS ) z10 start with z10.rn = 1
                                                connect by z10.id_expediente = prior z10.id_expediente and z10.rn = prior z10.rn + 1
                                              ) z20 where z20.islf = 1 */) vista_personas
on vista_personas.id_expediente=e.id_expediente
            
LEFT join ( 
select id_expediente,listagg(materia, ' / ')WITHIN GROUP (ORDER BY materia)materia_list,CALCULADO_SOLES_x
FROM (
SELECT x.ID_EXPEDIENTE, y.materia, x.CALCULADO_SOLES CALCULADO_SOLES_x FROM (
                                                SELECT ID_EXPEDIENTE, SUM(CALCULADO_SOLES)CALCULADO_SOLES  FROM 
                                                ( SELECT  CUTIA.ID_EXPEDIENTE,SUM(CUTIA.PRETENDIDO) * GESLEG.FUNC_GET_TIPO_CAMBIO(CUTIA.ID_MONEDA) CALCULADO_SOLES
                                                 FROM GESLEG.CUANTIA  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                                INNER JOIN GESLEG.MATERIA M ON cutia.id_materia=m.id_materia 
                                                INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                                WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                                                  FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                                                  GROUP BY CUTIA.ID_EXPEDIENTE,CUTIA.ID_MONEDA, mo.simbolo, m.descripcion order by id_expediente asc
                                                )GROUP BY ID_EXPEDIENTE ) x INNER JOIN 
                                            (SELECT  CUTIA.ID_EXPEDIENTE, m.id_materia || ' ' || m.descripcion || ' '|| '(' || mo.simbolo  || ' ' ||  SUM(CUTIA.PRETENDIDO) || ')'   MATERIA
                                            FROM GESLEG.CUANTIA  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                            INNER JOIN GESLEG.MATERIA M ON cutia.id_materia=m.id_materia 
                                            INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                 WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                 FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                 GROUP BY CUTIA.ID_EXPEDIENTE, CUTIA.ID_MONEDA,mo.simbolo, m.descripcion ,m.id_materia 
                                 order by id_expediente asc ) y on x.id_expediente=y.id_expediente) GROUP BY  id_expediente, CALCULADO_SOLES_x 
 
 /*select z20.id_expediente ,z20.MATERIA_list,CALCULADO_SOLES_x
              from (select  z10.id_expediente ,SUBSTR(SYS_CONNECT_BY_PATH(z10.MATERIA, '- '), 2) MATERIA_list, CALCULADO_SOLES_x,CONNECT_BY_ISLEAF islf
                                  from (select z.id_expediente,z.MATERIA,sum(CALCULADO_SOLES)CALCULADO_SOLES_x,row_number() over(partition by z.id_expediente order by z.MATERIA) rn
                                      from (SELECT x.ID_EXPEDIENTE, y.materia, x.CALCULADO_SOLES FROM (
                                                SELECT ID_EXPEDIENTE, SUM(CALCULADO_SOLES)CALCULADO_SOLES  FROM 
                                                ( SELECT  CUTIA.ID_EXPEDIENTE,SUM(CUTIA.PRETENDIDO) * GESLEG.FUNC_GET_TIPO_CAMBIO(CUTIA.ID_MONEDA) CALCULADO_SOLES
                                                 FROM GESLEG.CUANTIA  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                                INNER JOIN GESLEG.MATERIA M ON cutia.id_materia=m.id_materia 
                                                INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                                WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                                                  FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                                                  GROUP BY CUTIA.ID_EXPEDIENTE,CUTIA.ID_MONEDA, mo.simbolo, m.descripcion order by id_expediente asc
                                                )GROUP BY ID_EXPEDIENTE ) x INNER JOIN 
                                            (SELECT  CUTIA.ID_EXPEDIENTE, m.id_materia || ' ' || m.descripcion || ' '|| '(' || mo.simbolo  || ' ' ||  SUM(CUTIA.PRETENDIDO) || ')'   MATERIA
                                            FROM GESLEG.CUANTIA  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                            INNER JOIN GESLEG.MATERIA M ON cutia.id_materia=m.id_materia 
                                            INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                 WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                 FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                 GROUP BY CUTIA.ID_EXPEDIENTE, CUTIA.ID_MONEDA,mo.simbolo, m.descripcion ,m.id_materia 
                                 order by id_expediente asc ) y on x.id_expediente=y.id_expediente  )z
                                group by z.id_expediente,z.MATERIA
                                ) z10
                                start with z10.rn = 1 connect by z10.id_expediente = prior z10.id_expediente and z10.rn = prior z10.rn + 1
                                ) z20 where z20.islf = 1 */)VISTA_CUANTIA     
on VISTA_CUANTIA.id_expediente=e.id_expediente
         
LEFT JOIN (
select id_expediente,listagg(inculpados, ' / ')WITHIN GROUP (ORDER BY inculpados)INCULPADOS_list,CALCULADO_SOLES_x
FROM (SELECT x.ID_EXPEDIENTE, y.inculpados, x.CALCULADO_SOLES CALCULADO_SOLES_x  FROM (SELECT ID_EXPEDIENTE, SUM(CALCULADO_SOLES)CALCULADO_SOLES  FROM 
                                                ( SELECT  CUTIA.ID_EXPEDIENTE,
                                                 SUM(CUTIA.MONTO) * GESLEG.FUNC_GET_TIPO_CAMBIO(CUTIA.ID_MONEDA) CALCULADO_SOLES
                                                 FROM GESLEG.INCULPADO  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                                 INNER JOIN GESLEG.PERSONA M ON cutia.id_persona=m.id_persona
                                                 INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                                 WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                                FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                                GROUP BY CUTIA.ID_EXPEDIENTE, CUTIA.ID_MONEDA,mo.simbolo,m.nombre_completo  order by id_expediente asc
                                                )GROUP BY ID_EXPEDIENTE ) x INNER JOIN 
                                            (SELECT  CUTIA.ID_EXPEDIENTE,m.nombre_completo || ' '|| '(' || mo.simbolo  || ' ' ||  SUM(CUTIA.MONTO) || ')'   INCULPADOS
                                              FROM GESLEG.INCULPADO  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                              INNER JOIN GESLEG.PERSONA M ON cutia.id_persona=m.id_persona
                                              INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                              WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                              FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                          GROUP BY CUTIA.ID_EXPEDIENTE,CUTIA.ID_MONEDA, mo.simbolo,m.nombre_completo order by id_expediente asc ) y
                                          on x.id_expediente=y.id_expediente  ) GROUP BY  id_expediente, CALCULADO_SOLES_x
/*select z20.id_expediente,z20.INCULPADOS_list,CALCULADO_SOLES_x
                      from (select z10.id_expediente,SUBSTR(SYS_CONNECT_BY_PATH(z10.INCULPADOS, ','), 2) INCULPADOS_list, CALCULADO_SOLES_x ,CONNECT_BY_ISLEAF islf
                                  from (select z.id_expediente,z.INCULPADOS,sum(CALCULADO_SOLES)CALCULADO_SOLES_x,row_number() over(partition by z.id_expediente order by z.INCULPADOS) rn
                                      from (SELECT x.ID_EXPEDIENTE, y.inculpados, x.CALCULADO_SOLES FROM (SELECT ID_EXPEDIENTE, SUM(CALCULADO_SOLES)CALCULADO_SOLES  FROM 
                                                ( SELECT  CUTIA.ID_EXPEDIENTE,
                                                 SUM(CUTIA.MONTO) * GESLEG.FUNC_GET_TIPO_CAMBIO(CUTIA.ID_MONEDA) CALCULADO_SOLES
                                                 FROM GESLEG.INCULPADO  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                                 INNER JOIN GESLEG.PERSONA M ON cutia.id_persona=m.id_persona
                                                 INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                                 WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                                FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                                GROUP BY CUTIA.ID_EXPEDIENTE, CUTIA.ID_MONEDA,mo.simbolo,m.nombre_completo  order by id_expediente asc
                                                )GROUP BY ID_EXPEDIENTE ) x INNER JOIN 
                                            (SELECT  CUTIA.ID_EXPEDIENTE,m.nombre_completo || ' '|| '(' || mo.simbolo  || ' ' ||  SUM(CUTIA.MONTO) || ')'   INCULPADOS
                                              FROM GESLEG.INCULPADO  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                              INNER JOIN GESLEG.PERSONA M ON cutia.id_persona=m.id_persona
                                              INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                              WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                              FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                          GROUP BY CUTIA.ID_EXPEDIENTE,CUTIA.ID_MONEDA, mo.simbolo,m.nombre_completo order by id_expediente asc ) y
                                          on x.id_expediente=y.id_expediente  )z  group by z.id_expediente,z.INCULPADOS
                                        ) z10 start with z10.rn = 1 connect by z10.id_expediente = prior z10.id_expediente and z10.rn = prior z10.rn + 1
                                        ) z20 where z20.islf = 1 */) VISTA_CAUCION
on VISTA_CAUCION.id_expediente=e.id_expediente
   
left join (
        SELECT  e.ID_EXPEDIENTE,cutia.descripcion  || ' '|| '(' || mo.simbolo  || ' ' ||  SUM(e.importe_cautelar) || ')'   TIPOS
       ,SUM(e.importe_cautelar) * GESLEG.FUNC_GET_TIPO_CAMBIO(E.ID_MONEDA) CALCULADO_SOLES
              FROM GESLEG.tipo_cautelar  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON  cutia.id_tipo_cautelar=e.id_tipo_cautelar
              INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=e.id_moneda
              WHERE e.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
              FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
               GROUP BY  E.ID_EXPEDIENTE, E.ID_MONEDA,mo.simbolo,cutia.descripcion  order by id_expediente asc 
)   VISTA_MEDIDA_CAUTELAR    
ON VISTA_MEDIDA_CAUTELAR.id_expediente=e.id_Expediente 

LEFT join (
  select prevista.id_expediente,actividad_list || ' ' || p.fecha_actividad as ULT_ACTIVIDAD_PROCESAL_VIGENTE
          FROM    ( select id_expediente,listagg(actividad, ' / ')WITHIN GROUP (ORDER BY actividad)actividad_list
                    FROM (select c.nombre actividad, c.id_expediente
                                              from (
                                              select a.id_actividad , ac.nombre ,a.fecha_actividad , a.id_expediente
                                              from GESLEG.actividad_procesal a inner join 
                                                        ( select 
                                                          max(fecha_actividad) fecha_actividad, id_expediente
                                                          from GESLEG.actividad_procesal 
                                                          group by id_expediente order by id_expediente asc , fecha_actividad DESC
                                                        ) b on a.id_expediente = b.id_expediente and a.fecha_actividad = b.fecha_actividad
                                                          inner join GESLEG.actividad ac on a.id_actividad=ac.id_actividad )c 
                                                          ) GROUP BY  id_expediente
          
          
          /*select  z20.id_expediente, z20.actividad_list
              from ( select  z10.id_expediente
                              ,SUBSTR(SYS_CONNECT_BY_PATH(z10.actividad, ','), 2) actividad_list ,CONNECT_BY_ISLEAF islf
                                from ( select  z.id_expediente,z.actividad,row_number() over(partition by z.id_expediente order by z.actividad) rn
                                      from ( select c.nombre actividad, c.id_expediente
                                              from (
                                              select a.id_actividad , ac.nombre ,a.fecha_actividad , a.id_expediente
                                              from GESLEG.actividad_procesal a inner join 
                                                        ( select 
                                                          max(fecha_actividad) fecha_actividad, id_expediente
                                                          from GESLEG.actividad_procesal 
                                                          group by id_expediente order by id_expediente asc , fecha_actividad DESC
                                                        ) b on a.id_expediente = b.id_expediente and a.fecha_actividad = b.fecha_actividad
                                                          inner join GESLEG.actividad ac on a.id_actividad=ac.id_actividad )c )z 
                                                          group by z.id_expediente,z.actividad ) z10 start with z10.rn = 1
                                connect by z10.id_expediente = prior z10.id_expediente and z10.rn = prior z10.rn + 1
                              ) z20 where z20.islf = 1 */) prevista 
                              inner join 
                             
                                  ( select max(fecha_actividad) fecha_actividad, id_expediente
                                    from GESLEG.actividad_procesal 
                                    group by id_expediente order by id_expediente asc , fecha_actividad DESC
                                   )p
                               
                              on prevista.id_expediente=p.id_expediente
        ) vista_Ultima_Actividad
        
on vista_Ultima_Actividad.id_expediente=e.id_expediente   
  
ORDER BY  id_proceso asc ) e
  
  /**** INNER OUT *****/
   LEFT JOIN GESLEG.OFICINA o on o.id_oficina=e.id_oficina 
   LEFT JOIN GESLEG.TERRITORIO T ON T.ID_TERRITORIO= o.id_territorio 
   LEFT JOIN GESLEG.GRUPO_BANCA B on B.id_grupo_banca= T.ID_GRUPO_BANCA 
   LEFT JOIN GESLEG.VIA V on v.id_via=e.id_via
   LEFT JOIN GESLEG.INSTANCIA I on I.ID_INSTANCIA= e.id_instancia  
   LEFT JOIN GESLEG.Usuario U on U.id_usuario=e.id_usuario  
   LEFT JOIN GESLEG.ESTADO_EXPEDIENTE ex on ex.id_estado_expediente=e.id_estado_expediente 
   LEFT JOIN GESLEG.CALIFICACION C ON C.ID_CALIFICACION=e.id_calificacion 
   LEFT JOIN GESLEG.RIESGO R ON R.ID_RIESGO=e.id_riesgo
   LEFT JOIN GESLEG.PROCESO P ON P.ID_PROCESO=E.id_proceso  
   LEFT JOIN GESLEG.TIPO_EXPEDIENTE TP ON TP.ID_TIPO_EXPEDIENTE=E.ID_TIPO_EXPEDIENTE 
   LEFT JOIN GESLEG.ORGANO ORG ON ORG.ID_ORGANO=E.ID_ORGANO 
   LEFT JOIN GESLEG.RECURRENCIA REC ON REC.ID_RECURRENCIA=E.ID_RECURRENCIA
  
   ;

   
   
commit;
EXCEPTION
WHEN OTHERS THEN
    dbms_output.put_line('Error en la transaccion:'||SQLERRM);
     dbms_output.put_line('Se deshacen las modificaciones'||SQLCODE);
     ROLLBACK;
END;



COMMIT;

/

prompt =====================================================
prompt Creando o modificando el procedimiento "SP_ETL_DETALLADO"
prompt Tipo de reporte
prompt =====================================================

create or replace
PROCEDURE          "SP_ETL_DETALLADO" ( 
proceso        IN NUMBER,                                                                                            
via            IN NUMBER,                                                                                           
instancia      IN NUMBER,                                                                                          
responsable    IN VARCHAR2,                                                                                          
fechaInicio    IN DATE,                                                                                            
fechaFin       IN DATE,                                                                                           
banca          IN NUMBER,                                                                                            
territorio     IN NUMBER,                                                                                         
oficina        IN VARCHAR2,                                                                                         
p_departamento IN VARCHAR2,                                                                                          
p_provincia    IN VARCHAR2,                                                                                          
p_distrito     IN VARCHAR2,                                                                                         
tipoExpediente IN NUMBER,                                                                                            
calificacion   IN NUMBER,                                                                                           
organo         IN VARCHAR2,                                                                                          
recurrencia    IN VARCHAR2,                                                                                          
riesgo         IN NUMBER,                                                                                            
actProcesal    IN NUMBER,                                                                                            
materia        IN VARCHAR2,                                                                                          
p_estado       IN NUMBER,                                                                                            
tipoImporte    IN VARCHAR2,
moneda         IN NUMBER,
importeMinimo  IN NUMBER, 
importeMaximo  IN NUMBER,
p_nombre       IN VARCHAR2,
rol            IN NUMBER,                                                                                            
p_estudio      IN NUMBER,                                                                                            
abogado        IN VARCHAR2,
tipoUbigeo in number
)
IS
BEGIN
     execute immediate 'truncate table gesleg.FACT_DETALLADO';

/************************************************ INSERT TOTALES ***********************************************************************/
/***************************************************************************************************************************************/
 insert  INTO GESLEG.FACT_DETALLADO( 
ID_GRUPO_BANCA, GRUPO_BANCA, ID_TERRITORIO, TERRITORIO, ID_OFICINA, OFICINA, ID_PROCESO, PROCESO, 
 ID_VIA, VIA, ID_INSTANCIA, INSTANCIA, ID_RESPONSABLE, RESPONSABLE, NUMERO_EXPEDIENTE, FECHA_INICIO_PROCESO,
 ID_ESTADO_EXPEDIENTE, ESTADO_EXPEDIENTE, ID_CALIFICACION, CALIFICACION, ID_RECURRENCIA, RECURRENCIA, 
 ID_TIPO_EXPEDIENTE, TIPO_EXPEDIENTE, ID_ORGANO, ORGANO, SECRETARIO, ESTUDIOS_EXTERNOS, PERSONAS, MATERIAS,
 TOTAL_SOLES_MATERIA, INCULPADOS, TOTAL_SOLES_CAUCION, TIPOS, TOTAL_SOLES_MEDIDA_CAUTELAR, ID_RIESGO, RIESGO, 
 ULT_ACTIVIDAD_PROCESAL_VIGENTE
 ) 
 select 
nvl(b.id_grupo_banca,0)id_grupo_banca, NVL(b.descripcion, 'SIN BANCA')  GRUPO_BANCA, nvl(t.id_territorio,0)id_territorio, NVL(t.descripcion, 'SIN TERRITORIO') TERRITORIO,
nvl(e.id_oficina,0)id_oficina, e.id_oficina || ' ' || NVL(o.nombre,'SIN OFICINA') OFICINA,  e.id_proceso,P.NOMBRE PROCESO,
e.id_via, nvl(V.NOMBRE,' ') VIA, e.id_instancia, nvl(i.NOMBRE,' ') INSTANCIA,e.id_usuario  id_responsable, U.NOMBRE_COMPLETO RESPONSABLE,
e.numero_expediente, e.fecha_inicio_proceso, e.id_estado_expediente, ex.nombre ESTADO_EXPEDIENTE, e.id_calificacion,
nvl(C.NOMBRE,' ') CALIFICACION, e.id_recurrencia, nvl(REC.NOMBRE,' ') RECURRENCIA,e.id_tipo_expediente,nvl(TP.NOMBRE,' ') TIPO_EXPEDIENTE,
e.id_organo , ORG.NOMBRE ORGANO ,nvl(e.secretario,' ')secretario, nvl(E.ESTUDIOS_EXTERNOS,' ') ESTUDIOS_EXTERNOS, nvl(E.PERSONAS,' ') PERSONAS,
NVL(E.MATERIAS,' ')MATERIAS, NVL(E.TOTAL_SOLES_MATERIA, 0)TOTAL_SOLES_MATERIA,
NVL(E.INCULPADOS,' ')  INCULPADOS, NVL(E.TOTAL_SOLES_CAUCION ,0)TOTAL_SOLES_CAUCION,
NVL(E.TIPOS,' ')  TIPOS, NVL(E.TOTAL_SOLES_MEDIDA_CAUTELAR ,0)TOTAL_SOLES_MEDIDA_CAUTELAR ,
e.id_riesgo, nvl(R.DESCRIPCION,' ') RIESGO,  ULT_ACTIVIDAD_PROCESAL_VIGENTE


from ( 
select 

e.id_oficina,  e.id_proceso,
e.id_via,  e.id_instancia,e.id_usuario  , 
e.numero_expediente, e.fecha_inicio_proceso, e.id_estado_expediente,  e.id_calificacion, e.id_recurrencia, 
e.id_tipo_expediente,
e.id_organo , e.secretario, 
 vista.ABOGADO_list ESTUDIOS_EXTERNOS, vista_personas.PERSONAS_list PERSONAS,
NVL(VISTA_CUANTIA.MATERIA_list,'')MATERIAS, NVL(VISTA_CUANTIA.CALCULADO_SOLES_x,'')TOTAL_SOLES_MATERIA,
NVL(VISTA_CAUCION.INCULPADOS_list,'')  INCULPADOS, NVL(VISTA_CAUCION.CALCULADO_SOLES_x ,'')TOTAL_SOLES_CAUCION,
NVL(VISTA_MEDIDA_CAUTELAR.TIPOS,'')  TIPOS, NVL(VISTA_MEDIDA_CAUTELAR.CALCULADO_SOLES ,'')TOTAL_SOLES_MEDIDA_CAUTELAR ,
e.id_riesgo, NVL(vista_Ultima_Actividad.ULT_ACTIVIDAD_PROCESAL_VIGENTE ,' ') ULT_ACTIVIDAD_PROCESAL_VIGENTE

from 
(select e.id_expediente,
e.id_oficina,  e.id_proceso,
e.id_via,  e.id_instancia,e.id_usuario  , 
e.numero_expediente, e.fecha_inicio_proceso, e.id_estado_expediente,  e.id_calificacion, e.id_recurrencia, 
e.id_tipo_expediente,
e.id_organo , e.secretario, e.id_riesgo,  ' ' ULT_ACTIVIDAD_PROCESAL_VIGENTE
from GESLEG.expediente e

where e.exp_id_expediente is null 

 AND  (fechaInicio IS NULL OR fechaInicio ='' OR  e.fecha_inicio_proceso >=  fechaInicio )
 AND (fechaFin IS NULL OR fechaFin ='' OR   e.fecha_inicio_proceso <= fechaFin )
 AND (responsable IS NULL OR RESPONSABLE=0 OR e.id_usuario=responsable) 
  /** PROCESO VIA INSTANCIA*/
 AND  ( proceso =0 OR e.id_proceso=PROCESO )
 AND  (VIA =0 OR  e.id_via=VIA)
 AND  (INSTANCIA =0 OR  e.id_instancia= instancia     )
 
 AND  (BANCA = 0 OR 
      e.id_oficina in ( select id_oficina from GESLEG.oficina where id_territorio in  ( select id_territorio from GESLEG.territorio
                     where id_grupo_banca = (  select id_grupo_banca 
                                               from GESLEG.grupo_banca g 
                                               where g.id_grupo_banca= BANCA)) ) 
        )
 
 AND  (TERRITORIO = 0 or
      e.id_oficina in ( select id_oficina from GESLEG.oficina where id_territorio =TERRITORIO ) 
        )       
 AND  (OFICINA IS  NULL or e.id_oficina in OFICINA  )      
    
 --DEPARTAMENTO --
  AND (tipoUbigeo=2 AND (p_departamento IS NULL OR e.id_oficina in (select id_oficina from GESLEG.oficina where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_dep =p_departamento) )))
  and (tipoUbigeo=2 AND (p_provincia IS NULL OR e.id_oficina in ( select id_oficina from GESLEG.oficina where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_prov =p_provincia))))
  and (tipoUbigeo=2 and (p_distrito is null OR e.id_oficina in ( select id_oficina from GESLEG.oficina where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_dist =p_distrito) )) )
  
  
  and (tipoUbigeo=1 and (p_distrito is null OR e.id_organo in ( select id_organo from GESLEG.organo where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_dist =p_distrito) )) ) 
  
  and (tipoUbigeo=0 AND (p_departamento IS NULL OR e.id_oficina in (select id_oficina from GESLEG.oficina where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_dep =p_departamento) )))
  and (tipoUbigeo=0 and (p_provincia IS NULL OR e.id_oficina in ( select id_oficina from GESLEG.oficina where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_prov =p_provincia) )))
  and (tipoUbigeo=0 and (p_distrito is null OR e.id_oficina in ( select id_oficina from GESLEG.oficina where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_dist =p_distrito) )) )
  and (tipoUbigeo=0 and (p_distrito is null OR e.id_organo in ( select id_organo from GESLEG.organo where cod_dist in (select distinct U.COD_DIST FROM GESLEG.UBIGEO U WHERE u.cod_dist =p_distrito) )))
          
  AND (tipoExpediente = 0   OR E.ID_TIPO_EXPEDIENTE=tipoExpediente  )
  
  AND (calificacion =0 OR e.id_calificacion=calificacion  ) 
  
  AND (ORGANO IS NULL  OR E.ID_ORGANO=ORGANO  ) 
 
  AND (RECURRENCIA IS NULL OR E.ID_RECURRENCIA=RECURRENCIA  )
  
  AND (RIESGO =0 OR E.id_riesgo=RIESGO  )
  
  AND (actProcesal=0 OR  actProcesal IN ( SELECT ID_ACTIVIDAD  FROM ACTIVIDAD_PROCESAL WHERE ID_EXPEDIENTE=e.id_expediente) )
  
 -- AND (MATERIA IS NULL )
  
  AND (p_estado=0 OR e.id_estado_expediente =p_estado)
  
  
  /** Estudio y Abogado **/
  AND ( P_estudio=0 OR E.ID_EXPEDIENTE IN (SELECT  id_expediente from honorario
                          where id_abogado in ( SELECT id_abogado FROM GESLEG.abogado_estudio where estado = 'A' AND id_estudio=P_estudio)
                                           )  
      )
  AND ( ABOGADO IS NULL OR E.ID_EXPEDIENTE IN (SELECT  id_expediente from honorario
                          where id_abogado in ( SELECT id_abogado FROM GESLEG.abogado_estudio where estado = 'A' AND id_abogado=ABOGADO )
                         )  
      )  
  /** ROL Y PERSONA **/    
  
  AND (ROL =0 OR E.ID_EXPEDIENTE IN ( select id_Expediente from involucrado where id_rol_involucrado = ROL) )
  
  AND (p_nombre IS NULL OR E.ID_EXPEDIENTE IN ( select id_Expediente from GESLEG.involucrado where  id_persona=p_nombre ) )
   
  AND (MATERIA IS null  OR e.id_EXPEDIENTE IN (select id_expediente from GESLEG.CUANTIA where id_materia = MATERIA )  )    


  AND (tipoImporte is null 
  
    /** Importes cuantias **/
   or (tipoImporte=3 AND  e.id_EXPEDIENTE IN (  select id_expediente from (
                                                SELECT  CUTIA.ID_EXPEDIENTE, SUM(CUTIA.PRETENDIDO) MATERIA, mo.id_moneda id_monedax
                                            FROM GESLEG.CUANTIA  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                            INNER JOIN GESLEG.MATERIA M ON cutia.id_materia=m.id_materia 
                                            INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                             WHERE  EXP_ID_EXPEDIENTE IS NULL
                                             GROUP BY CUTIA.ID_EXPEDIENTE, CUTIA.ID_MONEDA,mo.id_moneda, m.descripcion ,m.id_materia 
                                             order by id_expediente asc )
                                             where materia between importeMinimo and importeMaximo   and (moneda = 0 or id_monedax =moneda) 
                                             )
      )
     /** Importes caucion **/ 
   or (tipoImporte=1 AND  e.id_EXPEDIENTE IN (  select id_expediente from (
                                                  SELECT  CUTIA.ID_EXPEDIENTE, CUTIA.id_moneda  , SUM(CUTIA.MONTO)   INCULPADOS
                                              FROM GESLEG.INCULPADO  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                              INNER JOIN GESLEG.PERSONA M ON cutia.id_persona=m.id_persona
                                              INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                              WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                              FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                              GROUP BY CUTIA.ID_EXPEDIENTE,CUTIA.ID_MONEDA order by id_expediente asc     )
                                              where INCULPADOS between importeMinimo and importeMaximo   and (moneda = 0 or id_moneda =moneda) 
                                             )
      )
       /** Importes medida cautelar **/
    or (tipoImporte=2 AND  e.id_EXPEDIENTE IN (  select id_expediente from (
                                                  SELECT  e.ID_EXPEDIENTE, e.id_moneda  , SUM(e.importe_cautelar)TIPOS
                                                  FROM GESLEG.tipo_cautelar  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON  cutia.id_tipo_cautelar=e.id_tipo_cautelar
                                                  INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=e.id_moneda
                                                  WHERE e.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                                  FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                                  GROUP BY  E.ID_EXPEDIENTE, E.ID_MONEDA,cutia.descripcion  order by id_expediente asc )
                                              where TIPOS between importeMinimo and importeMaximo   and (moneda = 0 or id_moneda =moneda) 
                                             )
     )                                        
   ) -- Fin Tipo IMporte
     

   
) e


LEFT join ( 
select id_expediente,listagg(ABOGADO, ' / ')WITHIN GROUP (ORDER BY ABOGADO)ABOGADO_list
FROM (select h.id_expediente,e.nombre||'('|| a.nombre_completo||')' ABOGADO
                                from GESLEG.honorario h inner join   GESLEG.abogado_estudio ae on h.id_abogado=ae.id_abogado
                                inner join GESLEG.estudio e on ae.id_estudio=e.id_estudio 
                                inner join GESLEG.abogado a on a.id_abogado=ae.id_abogado 
                                ) GROUP BY  id_expediente

  /*select  z20.id_expediente, z20.ABOGADO_list
              from ( select  z10.id_expediente
                              ,SUBSTR(SYS_CONNECT_BY_PATH(z10.ABOGADO, ','), 2) ABOGADO_list ,CONNECT_BY_ISLEAF islf
                                from ( select  z.id_expediente,z.ABOGADO,row_number() over(partition by z.id_expediente order by z.ABOGADO) rn
                                from (select h.id_expediente,e.nombre||'('|| a.nombre_completo||')' ABOGADO
                                from GESLEG.honorario h inner join   GESLEG.abogado_estudio ae on h.id_abogado=ae.id_abogado
                                inner join GESLEG.estudio e on ae.id_estudio=e.id_estudio 
                                inner join GESLEG.abogado a on a.id_abogado=ae.id_abogado 
                                )z group by z.id_expediente,z.ABOGADO ) z10 start with z10.rn = 1
                                connect by z10.id_expediente = prior z10.id_expediente and z10.rn = prior z10.rn + 1
                              ) z20 where z20.islf = 1 */) vista
on vista.id_expediente=e.id_expediente
            
LEFT join (
select id_expediente,listagg(PERSONAS, ',')WITHIN GROUP (ORDER BY PERSONAS)PERSONAS_list
FROM (
select id_expediente, r.nombre || ' ' ||p.numero_documento||' ' ||p.nombre_completo PERSONAS
from GESLEG.involucrado i inner join GESLEG.rol_involucrado r ON i.id_rol_involucrado=r.id_rol_involucrado 
INNER JOIN GESLEG.PERSONA P ON p.id_persona=i.id_persona
order by id_expediente  asc 
) GROUP BY  id_expediente 

/* select  z20.id_expediente, z20.PERSONAS_list
             from ( select z10.id_expediente ,SUBSTR(SYS_CONNECT_BY_PATH(z10.PERSONAS, ','), 2) PERSONAS_list ,CONNECT_BY_ISLEAF islf
                                     from (select  z.id_expediente,z.PERSONAS,row_number() over(partition by z.id_expediente order by z.PERSONAS) rn
                                             from ( select id_expediente, r.nombre || ' ' ||p.numero_documento||' ' ||p.nombre_completo PERSONAS
                                             from GESLEG.involucrado i inner join GESLEG.rol_involucrado r ON i.id_rol_involucrado=r.id_rol_involucrado 
                                            INNER JOIN GESLEG.PERSONA P ON p.id_persona=i.id_persona
                                            order by id_expediente  asc
                                              )z group by z.id_expediente,z.PERSONAS ) z10 start with z10.rn = 1
                                                connect by z10.id_expediente = prior z10.id_expediente and z10.rn = prior z10.rn + 1
                                              ) z20 where z20.islf = 1 */) vista_personas
on vista_personas.id_expediente=e.id_expediente
            
LEFT join ( 
select id_expediente,listagg(materia, ' / ')WITHIN GROUP (ORDER BY materia)materia_list,CALCULADO_SOLES_x
FROM (
SELECT x.ID_EXPEDIENTE, y.materia, x.CALCULADO_SOLES CALCULADO_SOLES_x FROM (
                                                SELECT ID_EXPEDIENTE, SUM(CALCULADO_SOLES)CALCULADO_SOLES  FROM 
                                                ( SELECT  CUTIA.ID_EXPEDIENTE,SUM(CUTIA.PRETENDIDO) * GESLEG.FUNC_GET_TIPO_CAMBIO(CUTIA.ID_MONEDA) CALCULADO_SOLES
                                                 FROM GESLEG.CUANTIA  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                                INNER JOIN GESLEG.MATERIA M ON cutia.id_materia=m.id_materia 
                                                INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                                WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                                                  FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                                                  GROUP BY CUTIA.ID_EXPEDIENTE,CUTIA.ID_MONEDA, mo.simbolo, m.descripcion order by id_expediente asc
                                                )GROUP BY ID_EXPEDIENTE ) x INNER JOIN 
                                            (SELECT  CUTIA.ID_EXPEDIENTE, m.id_materia || ' ' || m.descripcion || ' '|| '(' || mo.simbolo  || ' ' ||  SUM(CUTIA.PRETENDIDO) || ')'   MATERIA
                                            FROM GESLEG.CUANTIA  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                            INNER JOIN GESLEG.MATERIA M ON cutia.id_materia=m.id_materia 
                                            INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                 WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                 FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                 GROUP BY CUTIA.ID_EXPEDIENTE, CUTIA.ID_MONEDA,mo.simbolo, m.descripcion ,m.id_materia 
                                 order by id_expediente asc ) y on x.id_expediente=y.id_expediente) GROUP BY  id_expediente, CALCULADO_SOLES_x 
 
 /*select z20.id_expediente ,z20.MATERIA_list,CALCULADO_SOLES_x
              from (select  z10.id_expediente ,SUBSTR(SYS_CONNECT_BY_PATH(z10.MATERIA, '- '), 2) MATERIA_list, CALCULADO_SOLES_x,CONNECT_BY_ISLEAF islf
                                  from (select z.id_expediente,z.MATERIA,sum(CALCULADO_SOLES)CALCULADO_SOLES_x,row_number() over(partition by z.id_expediente order by z.MATERIA) rn
                                      from (SELECT x.ID_EXPEDIENTE, y.materia, x.CALCULADO_SOLES FROM (
                                                SELECT ID_EXPEDIENTE, SUM(CALCULADO_SOLES)CALCULADO_SOLES  FROM 
                                                ( SELECT  CUTIA.ID_EXPEDIENTE,SUM(CUTIA.PRETENDIDO) * GESLEG.FUNC_GET_TIPO_CAMBIO(CUTIA.ID_MONEDA) CALCULADO_SOLES
                                                 FROM GESLEG.CUANTIA  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                                INNER JOIN GESLEG.MATERIA M ON cutia.id_materia=m.id_materia 
                                                INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                                WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                                                  FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                                                  GROUP BY CUTIA.ID_EXPEDIENTE,CUTIA.ID_MONEDA, mo.simbolo, m.descripcion order by id_expediente asc
                                                )GROUP BY ID_EXPEDIENTE ) x INNER JOIN 
                                            (SELECT  CUTIA.ID_EXPEDIENTE, m.id_materia || ' ' || m.descripcion || ' '|| '(' || mo.simbolo  || ' ' ||  SUM(CUTIA.PRETENDIDO) || ')'   MATERIA
                                            FROM GESLEG.CUANTIA  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                            INNER JOIN GESLEG.MATERIA M ON cutia.id_materia=m.id_materia 
                                            INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                 WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                 FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                 GROUP BY CUTIA.ID_EXPEDIENTE, CUTIA.ID_MONEDA,mo.simbolo, m.descripcion ,m.id_materia 
                                 order by id_expediente asc ) y on x.id_expediente=y.id_expediente  )z
                                group by z.id_expediente,z.MATERIA
                                ) z10
                                start with z10.rn = 1 connect by z10.id_expediente = prior z10.id_expediente and z10.rn = prior z10.rn + 1
                                ) z20 where z20.islf = 1 */)VISTA_CUANTIA     
on VISTA_CUANTIA.id_expediente=e.id_expediente
         
LEFT JOIN (
select id_expediente,listagg(inculpados, ' / ')WITHIN GROUP (ORDER BY inculpados)INCULPADOS_list,CALCULADO_SOLES_x
FROM (SELECT x.ID_EXPEDIENTE, y.inculpados, x.CALCULADO_SOLES CALCULADO_SOLES_x  FROM (SELECT ID_EXPEDIENTE, SUM(CALCULADO_SOLES)CALCULADO_SOLES  FROM 
                                                ( SELECT  CUTIA.ID_EXPEDIENTE,
                                                 SUM(CUTIA.MONTO) * GESLEG.FUNC_GET_TIPO_CAMBIO(CUTIA.ID_MONEDA) CALCULADO_SOLES
                                                 FROM GESLEG.INCULPADO  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                                 INNER JOIN GESLEG.PERSONA M ON cutia.id_persona=m.id_persona
                                                 INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                                 WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                                FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                                GROUP BY CUTIA.ID_EXPEDIENTE, CUTIA.ID_MONEDA,mo.simbolo,m.nombre_completo  order by id_expediente asc
                                                )GROUP BY ID_EXPEDIENTE ) x INNER JOIN 
                                            (SELECT  CUTIA.ID_EXPEDIENTE,m.nombre_completo || ' '|| '(' || mo.simbolo  || ' ' ||  SUM(CUTIA.MONTO) || ')'   INCULPADOS
                                              FROM GESLEG.INCULPADO  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                              INNER JOIN GESLEG.PERSONA M ON cutia.id_persona=m.id_persona
                                              INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                              WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                              FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                          GROUP BY CUTIA.ID_EXPEDIENTE,CUTIA.ID_MONEDA, mo.simbolo,m.nombre_completo order by id_expediente asc ) y
                                          on x.id_expediente=y.id_expediente  ) GROUP BY  id_expediente, CALCULADO_SOLES_x
/*select z20.id_expediente,z20.INCULPADOS_list,CALCULADO_SOLES_x
                      from (select z10.id_expediente,SUBSTR(SYS_CONNECT_BY_PATH(z10.INCULPADOS, ','), 2) INCULPADOS_list, CALCULADO_SOLES_x ,CONNECT_BY_ISLEAF islf
                                  from (select z.id_expediente,z.INCULPADOS,sum(CALCULADO_SOLES)CALCULADO_SOLES_x,row_number() over(partition by z.id_expediente order by z.INCULPADOS) rn
                                      from (SELECT x.ID_EXPEDIENTE, y.inculpados, x.CALCULADO_SOLES FROM (SELECT ID_EXPEDIENTE, SUM(CALCULADO_SOLES)CALCULADO_SOLES  FROM 
                                                ( SELECT  CUTIA.ID_EXPEDIENTE,
                                                 SUM(CUTIA.MONTO) * GESLEG.FUNC_GET_TIPO_CAMBIO(CUTIA.ID_MONEDA) CALCULADO_SOLES
                                                 FROM GESLEG.INCULPADO  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                                 INNER JOIN GESLEG.PERSONA M ON cutia.id_persona=m.id_persona
                                                 INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                                 WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                                FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                                GROUP BY CUTIA.ID_EXPEDIENTE, CUTIA.ID_MONEDA,mo.simbolo,m.nombre_completo  order by id_expediente asc
                                                )GROUP BY ID_EXPEDIENTE ) x INNER JOIN 
                                            (SELECT  CUTIA.ID_EXPEDIENTE,m.nombre_completo || ' '|| '(' || mo.simbolo  || ' ' ||  SUM(CUTIA.MONTO) || ')'   INCULPADOS
                                              FROM GESLEG.INCULPADO  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON CUTIA.ID_EXPEDIENTE=E.ID_EXPEDIENTE
                                              INNER JOIN GESLEG.PERSONA M ON cutia.id_persona=m.id_persona
                                              INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=cutia.id_moneda
                                              WHERE CUTIA.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
                                              FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
                                          GROUP BY CUTIA.ID_EXPEDIENTE,CUTIA.ID_MONEDA, mo.simbolo,m.nombre_completo order by id_expediente asc ) y
                                          on x.id_expediente=y.id_expediente  )z  group by z.id_expediente,z.INCULPADOS
                                        ) z10 start with z10.rn = 1 connect by z10.id_expediente = prior z10.id_expediente and z10.rn = prior z10.rn + 1
                                        ) z20 where z20.islf = 1 */) VISTA_CAUCION
on VISTA_CAUCION.id_expediente=e.id_expediente
   
left join (
        SELECT  e.ID_EXPEDIENTE,cutia.descripcion  || ' '|| '(' || mo.simbolo  || ' ' ||  SUM(e.importe_cautelar) || ')'   TIPOS
       ,SUM(e.importe_cautelar) * GESLEG.FUNC_GET_TIPO_CAMBIO(E.ID_MONEDA) CALCULADO_SOLES
              FROM GESLEG.tipo_cautelar  CUTIA  INNER JOIN  GESLEG.EXPEDIENTE E ON  cutia.id_tipo_cautelar=e.id_tipo_cautelar
              INNER JOIN GESLEG.MONEDA MO ON mo.id_moneda=e.id_moneda
              WHERE e.ID_EXPEDIENTE IN (SELECT ID_EXPEDIENTE
              FROM GESLEG.EXPEDIENTE WHERE  EXP_ID_EXPEDIENTE IS NULL )
               GROUP BY  E.ID_EXPEDIENTE, E.ID_MONEDA,mo.simbolo,cutia.descripcion  order by id_expediente asc 
)   VISTA_MEDIDA_CAUTELAR    
ON VISTA_MEDIDA_CAUTELAR.id_expediente=e.id_Expediente 

LEFT join (
  select prevista.id_expediente,actividad_list || ' ' || p.fecha_actividad as ULT_ACTIVIDAD_PROCESAL_VIGENTE
          FROM    ( select id_expediente,listagg(actividad, ' / ')WITHIN GROUP (ORDER BY actividad)actividad_list
                    FROM (select c.nombre actividad, c.id_expediente
                                              from (
                                              select a.id_actividad , ac.nombre ,a.fecha_actividad , a.id_expediente
                                              from GESLEG.actividad_procesal a inner join 
                                                        ( select 
                                                          max(fecha_actividad) fecha_actividad, id_expediente
                                                          from GESLEG.actividad_procesal 
                                                          group by id_expediente order by id_expediente asc , fecha_actividad DESC
                                                        ) b on a.id_expediente = b.id_expediente and a.fecha_actividad = b.fecha_actividad
                                                          inner join GESLEG.actividad ac on a.id_actividad=ac.id_actividad )c 
                                                          ) GROUP BY  id_expediente
          
          
          /*select  z20.id_expediente, z20.actividad_list
              from ( select  z10.id_expediente
                              ,SUBSTR(SYS_CONNECT_BY_PATH(z10.actividad, ','), 2) actividad_list ,CONNECT_BY_ISLEAF islf
                                from ( select  z.id_expediente,z.actividad,row_number() over(partition by z.id_expediente order by z.actividad) rn
                                      from ( select c.nombre actividad, c.id_expediente
                                              from (
                                              select a.id_actividad , ac.nombre ,a.fecha_actividad , a.id_expediente
                                              from GESLEG.actividad_procesal a inner join 
                                                        ( select 
                                                          max(fecha_actividad) fecha_actividad, id_expediente
                                                          from GESLEG.actividad_procesal 
                                                          group by id_expediente order by id_expediente asc , fecha_actividad DESC
                                                        ) b on a.id_expediente = b.id_expediente and a.fecha_actividad = b.fecha_actividad
                                                          inner join GESLEG.actividad ac on a.id_actividad=ac.id_actividad )c )z 
                                                          group by z.id_expediente,z.actividad ) z10 start with z10.rn = 1
                                connect by z10.id_expediente = prior z10.id_expediente and z10.rn = prior z10.rn + 1
                              ) z20 where z20.islf = 1 */) prevista 
                              inner join 
                             
                                  ( select max(fecha_actividad) fecha_actividad, id_expediente
                                    from GESLEG.actividad_procesal 
                                    group by id_expediente order by id_expediente asc , fecha_actividad DESC
                                   )p
                               
                              on prevista.id_expediente=p.id_expediente
        ) vista_Ultima_Actividad
        
on vista_Ultima_Actividad.id_expediente=e.id_expediente   
  
ORDER BY  id_proceso asc ) e
  
  /**** INNER OUT *****/
   LEFT JOIN GESLEG.OFICINA o on o.id_oficina=e.id_oficina 
   LEFT JOIN GESLEG.TERRITORIO T ON T.ID_TERRITORIO= o.id_territorio 
   LEFT JOIN GESLEG.GRUPO_BANCA B on B.id_grupo_banca= T.ID_GRUPO_BANCA 
   LEFT JOIN GESLEG.VIA V on v.id_via=e.id_via
   LEFT JOIN GESLEG.INSTANCIA I on I.ID_INSTANCIA= e.id_instancia  
   LEFT JOIN GESLEG.Usuario U on U.id_usuario=e.id_usuario  
   LEFT JOIN GESLEG.ESTADO_EXPEDIENTE ex on ex.id_estado_expediente=e.id_estado_expediente 
   LEFT JOIN GESLEG.CALIFICACION C ON C.ID_CALIFICACION=e.id_calificacion 
   LEFT JOIN GESLEG.RIESGO R ON R.ID_RIESGO=e.id_riesgo
   LEFT JOIN GESLEG.PROCESO P ON P.ID_PROCESO=E.id_proceso  
   LEFT JOIN GESLEG.TIPO_EXPEDIENTE TP ON TP.ID_TIPO_EXPEDIENTE=E.ID_TIPO_EXPEDIENTE 
   LEFT JOIN GESLEG.ORGANO ORG ON ORG.ID_ORGANO=E.ID_ORGANO 
   LEFT JOIN GESLEG.RECURRENCIA REC ON REC.ID_RECURRENCIA=E.ID_RECURRENCIA
  
   ;

   
   
commit;
EXCEPTION
WHEN OTHERS THEN
    dbms_output.put_line('Error en la transaccion:'||SQLERRM);
     dbms_output.put_line('Se deshacen las modificaciones'||SQLCODE);
     ROLLBACK;
END;


COMMIT;

/

prompt =====================================================
prompt Creando la vista ACTIVIDADXEXPEDIENTE
prompt Actividades por expediente
prompt =====================================================


CREATE OR REPLACE FORCE VIEW "GESLEG"."ACTIVIDADXEXPEDIENTE" ("ROW_ID", "NUMERO_EXPEDIENTE", "INSTANCIA", "VIA", "ID_ROL_INVOLUCRADO", "ID_INVOLUCRADO", "ID_ORGANO", "APELLIDO_PATERNO", "ID_PROCESO", "ID_OFICINA", "ID_EXPEDIENTE", "ID_RECURRENCIA", "ID_ESTADO_EXPEDIENTE", "CORREO", "ID_USUARIO", "ORGANO", "ACTIVIDAD", "FECHA_ACTIVIDAD", "FECHA_VENCIMIENTO", "PLAZO_LEY", "FECHA_ATENCION", "COLOR")
                                                          AS
  SELECT ROW_NUMBER() OVER (ORDER BY c.numero_expediente) AS ROW_ID,
    c.numero_expediente,
    ins.nombre                    AS instancia,
    vi.id_via                     AS via,
    NVL(inv.id_rol_involucrado,0) AS id_rol_involucrado,
    NVL(inv.id_involucrado,0)     AS id_involucrado,
    org.id_organo,
    usu.Nombre_Completo AS apellido_paterno,
    pro.id_proceso,
    ofic.id_oficina,
    c.id_expediente,
    NVL(rec.id_recurrencia,0) AS id_recurrencia,
    est.id_estado_expediente,
    usu.correo     AS correo,
    usu.id_usuario AS id_usuario,
    org.nombre     AS organo,
    act.nombre     AS Actividad,
    a.fecha_actividad,
    a.fecha_vencimiento,
    a.plazo_ley,
    a.fecha_atencion,
    CASE
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= 0
      THEN 'R'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad=a.id_actividad
        AND id_via        =vi.id_via
        AND id_proceso    = c.id_proceso
        AND color         ='R'
        AND estado        = 'A'
        )
      THEN 'R'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad=a.id_actividad
        AND id_via        =vi.id_via
        AND id_proceso    = c.id_proceso
        AND color         ='N'
        AND estado        = 'A'
        )
      THEN 'N'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad=a.id_actividad
        AND id_via        =vi.id_via
        AND id_proceso    = c.id_proceso
        AND color         ='A'
        AND estado        = 'A'
        )
      THEN 'A'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad IS NULL
        AND id_via          =vi.id_via
        AND id_proceso      = c.id_proceso
        AND color           ='R'
        AND estado          = 'A'
        )
      THEN 'R'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad IS NULL
        AND id_via          = vi.id_via
        AND id_proceso      = c.id_proceso
        AND color           ='N'
        AND estado          = 'A'
        )
      THEN 'N'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad IS NULL
        AND id_via          =vi.id_via
        AND id_proceso      = c.id_proceso
        AND color           ='A'
        AND estado          = 'A'
        )
      THEN 'A'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad IS NULL
        AND id_via         IS NULL
        AND id_proceso      = c.id_proceso
        AND color           ='R'
        AND estado          = 'A'
        )
      THEN 'R'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad IS NULL
        AND id_via         IS NULL
        AND id_proceso      = c.id_proceso
        AND color           ='N'
        AND estado          = 'A'
        )
      THEN 'N'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad IS NULL
        AND id_via         IS NULL
        AND id_proceso      = c.id_proceso
        AND color           ='A'
        AND estado          = 'A'
        )
      THEN 'A'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) > 0
      THEN 'V'
      WHEN DAYS(a.fecha_actividad,SYSDATE) <= 0
      THEN 'V'
      ELSE 'E'
    END AS COLOR
  FROM actividad_procesal a
  LEFT OUTER JOIN expediente c
  ON a.id_expediente=c.id_expediente
  LEFT OUTER JOIN involucrado inv
  ON c.id_expediente=inv.id_expediente
  LEFT OUTER JOIN persona per
  ON inv.id_persona=per.id_persona
  LEFT OUTER JOIN organo org
  ON c.id_organo = org.id_organo
  LEFT OUTER JOIN actividad act
  ON a.id_actividad = act.id_actividad
  LEFT OUTER JOIN instancia ins
  ON c.id_instancia=ins.id_instancia
  LEFT OUTER JOIN via vi
  ON ins.id_via = vi.id_via
  LEFT OUTER JOIN proceso pro
  ON vi.id_proceso = pro.id_proceso
  LEFT OUTER JOIN usuario usu
  ON c.id_usuario =usu.id_usuario
  LEFT OUTER JOIN oficina ofic
  ON ofic.id_oficina=c.id_oficina
  LEFT OUTER JOIN recurrencia rec
  ON rec.id_recurrencia = c.id_recurrencia
  LEFT OUTER JOIN estado_expediente est
  ON est.id_estado_expediente  = c.id_estado_expediente
  WHERE A.Fecha_Atencion      IS NULL
  AND A.Id_Situacion_Act_Proc <> 2
  ORDER BY C.Numero_Expediente;
  
  
  COMMIT;

/



prompt =====================================================
prompt Creando la vista BUSQUEDAACTPROCESAL
prompt Búsqueda por expediente
prompt =====================================================

CREATE OR REPLACE FORCE VIEW "GESLEG"."BUSQUEDAACTPROCESAL" ("ROW_ID", "NUMERO_EXPEDIENTE", "DEMANDANTE", "ORGANO", "ACTIVIDAD", "RESPONSABLE", "ID_OFICINA", "ID_RECURRENCIA", "HORA", "FECHA_ACTIVIDAD", "FECHA_VENCIMIENTO", "FECHA_ATENCION", "OBSERVACION", "ID_ESTADO_EXPEDIENTE", "ID_PROCESO", "ID_VIA", "ID_ACTIVIDAD", "ID_INSTANCIA", "ID_EXPEDIENTE", "PLAZO_LEY", "ID_INVOLUCRADO", "ID_ORGANO", "ID_USUARIO", "ID_ACTIVIDAD_PROCESAL", "COLOR")
                                                                   AS
  SELECT DISTINCT ROW_NUMBER() OVER (ORDER BY c.numero_expediente) AS ROW_ID,
    c.Numero_Expediente,
    NVL(vista_personas.Demandante,' ') AS DEMANDANTE,
    org.nombre                         AS Organo,
    act.nombre                         AS Actividad,
    Usu.Nombre_Completo                AS Responsable,
    ofic.id_oficina,
    NVL(rec.id_recurrencia,0)                AS id_recurrencia,
    TO_CHAR(a.fecha_actividad, 'HH24:MI:SS') AS Hora,
    a.fecha_actividad,
    a.fecha_vencimiento,
    a.fecha_atencion,
    a.observacion,
    est.id_estado_expediente,
    pro.id_proceso,
    vi.id_via,
    act.id_actividad,
    c.id_instancia,
    c.id_expediente,
    a.plazo_ley,
    NVL(inv.id_involucrado,0) AS id_involucrado,
    org.id_organo,
    usu.id_usuario AS id_usuario,
    a.Id_Actividad_Procesal,
    CASE
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= 0
      THEN 'R'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad=a.id_actividad
        AND id_via        =vi.id_via
        AND id_proceso    = c.id_proceso
        AND color         ='R'
        AND estado        = 'A'
        )
      THEN 'R'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad IS NULL
        AND id_via          =vi.id_via
        AND id_proceso      = c.id_proceso
        AND color           ='R'
        AND estado          = 'A'
        )
      THEN 'R'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad IS NULL
        AND id_via         IS NULL
        AND id_proceso      = c.id_proceso
        AND color           ='R'
        AND estado          = 'A'
        )
      THEN 'R'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad=a.id_actividad
        AND id_via        =vi.id_via
        AND id_proceso    = c.id_proceso
        AND color         ='N'
        AND estado        = 'A'
        )
      THEN 'N'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad IS NULL
        AND id_via          = vi.id_via
        AND id_proceso      = c.id_proceso
        AND color           ='N'
        AND estado          = 'A'
        )
      THEN 'N'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad IS NULL
        AND id_via         IS NULL
        AND id_proceso      = c.id_proceso
        AND color           ='N'
        AND estado          = 'A'
        )
      THEN 'N'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad=a.id_actividad
        AND id_via        =vi.id_via
        AND id_proceso    = c.id_proceso
        AND color         ='A'
        AND estado        = 'A'
        )
      THEN 'A'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad IS NULL
        AND id_via          =vi.id_via
        AND id_proceso      = c.id_proceso
        AND color           ='A'
        AND estado          = 'A'
        )
      THEN 'A'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=
        (SELECT dias
        FROM aviso
        WHERE id_actividad IS NULL
        AND id_via         IS NULL
        AND id_proceso      = c.id_proceso
        AND color           ='A'
        AND estado          = 'A'
        )
      THEN 'A'
      WHEN DAYS(SYSDATE,a.fecha_vencimiento) > 0
      THEN 'V'
      WHEN DAYS(a.fecha_actividad,SYSDATE) <= 0
      THEN 'V'
      ELSE 'E'
    END AS COLOR
  FROM actividad_procesal a
  LEFT OUTER JOIN expediente c
  ON a.id_expediente=c.id_expediente
  LEFT OUTER JOIN involucrado inv
  ON c.id_expediente=inv.id_expediente
  LEFT OUTER JOIN persona per
  ON inv.id_persona=per.id_persona
  LEFT OUTER JOIN organo org
  ON c.id_organo = org.id_organo
  LEFT OUTER JOIN actividad act
  ON a.id_actividad = act.id_actividad
  LEFT OUTER JOIN instancia ins
  ON c.id_instancia=ins.id_instancia
  LEFT OUTER JOIN via vi
  ON ins.id_via = vi.id_via
  LEFT OUTER JOIN proceso pro
  ON vi.id_proceso = pro.id_proceso
  LEFT OUTER JOIN usuario usu
  ON C.Id_Usuario =Usu.Id_Usuario
  LEFT OUTER JOIN oficina ofic
  ON ofic.id_oficina=c.id_oficina
  LEFT OUTER JOIN estado_expediente est
  ON est.id_estado_expediente = c.id_estado_expediente
  LEFT OUTER JOIN recurrencia rec
  ON rec.id_recurrencia = c.id_recurrencia
  LEFT JOIN
    (SELECT z20.id_expediente,
      z20.PERSONAS_list Demandante
    FROM
      (SELECT z10.id_expediente ,
        SUBSTR(SYS_CONNECT_BY_PATH(z10.PERSONAS, '/'), 2) PERSONAS_list ,
        CONNECT_BY_ISLEAF islf
      FROM
        (SELECT z.id_expediente,
          z.PERSONAS,
          row_number() over(partition BY z.id_expediente order by z.PERSONAS) rn
        FROM
          (SELECT c.id_expediente,
            per.nombre_completo PERSONAS
          FROM expediente c
          LEFT OUTER JOIN involucrado inv
          ON c.id_expediente=inv.id_expediente
          LEFT OUTER JOIN persona per
          ON inv.id_persona             =per.id_persona
          WHERE inv.id_rol_involucrado IN (2,4)
          )z
        GROUP BY z.id_expediente,
          z.PERSONAS
        ) z10
        START WITH z10.rn            = 1
        CONNECT BY z10.id_expediente = prior z10.id_expediente
      AND z10.rn                     = prior z10.rn + 1
      ) z20
    WHERE z20.islf                                  = 1
    ) vista_personas ON vista_personas.id_expediente=a.id_expediente
  WHERE A.Fecha_Atencion                           IS NULL
  AND a.id_situacion_act_proc                      <> 2
  ORDER BY C.Numero_Expediente ,
    a.id_actividad_procesal;






prompt =========================
prompt Ejecucion de sentencias realizada con exito.
prompt =========================
prompt  

spool off;