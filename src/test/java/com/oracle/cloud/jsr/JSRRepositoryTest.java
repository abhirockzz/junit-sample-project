package com.oracle.cloud.jsr;

import com.oracle.cloud.jsr.jpa.JPAFacade;
import com.oracle.cloud.jsr.jpa.JSRRepository;
import com.oracle.cloud.jsr.jpa.entities.JavaEESpecification;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class JSRRepositoryTest {

    public JSRRepositoryTest() {
    }
    static Map<String, String> props = new HashMap<>();
    final static String PU_NAME = "derby-in-memory-PU";

    @BeforeClass
    public static void setUpClass() {

        props.put("javax.persistence.jdbc.url", "jdbc:derby:target/derbydb;create=true");
        props.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
        JPAFacade.bootstrapEMF(PU_NAME, props);

    }

    @AfterClass
    public static void tearDownClass() {
        props.clear();
        props = null;

        JPAFacade.closeEMF();
    }

    JSRRepository cut;

    @Before
    public void setUp() {
        cut = new JSRRepository();
    }

    @After
    public void tearDown() {
        //nothing to do
    }

    @Test
    public void getSingleJSRTest() {
        JavaEESpecification spec = cut.get("123");
        assertNotNull("Spec was null!", spec);
        assertEquals("Wrong spec id", spec.getJsrId(), new Integer(123));
        assertEquals("Wrong spec name", spec.getName(), "jsonb");
    }

    @Test(expected = RuntimeException.class)
    public void getSingleJSRTestForNullValue() {
        cut.get(null);

    }

    @Test(expected = RuntimeException.class)
    public void getSingleJSRTestForBlankValue() {
        cut.get("");

    }

    @Test
    public void getSingleJSRTestForInvalidValue() {
        JavaEESpecification spec = cut.get("007");
        assertNull("Spec was not null!", spec);
    }

    @Test
    public void getAllJSRsTest() {
        List<JavaEESpecification> specs = cut.all();
        assertNotNull("Specs list was null!", specs);
        assertEquals("2 specs were not found", specs.size(), 2);
    }

    @Test
    public void createNewJSRTest() {
        JavaEESpecification newSpec = new JavaEESpecification(366, "Java EE Platform", "8");
        cut.newJSR(newSpec);
        JavaEESpecification spec = cut.get("366");
        assertNotNull("Spec was null!", spec);
        assertEquals("Wrong spec id", spec.getJsrId(), new Integer(366));
        assertEquals("Wrong spec name", spec.getName(), "Java EE Platform");
        assertEquals("Wrong spec version", spec.getVersion(), "8");
    }

    @Test
    public void updateJSRDescTest() {

        String specID = "375";
        String oldDesc = "security for the Java EE platform";
        String newDesc = "updated desc on " + new Date();

        JavaEESpecification newSpec = new JavaEESpecification(Integer.parseInt(specID), oldDesc, "Security", "1.0");
        cut.newJSR(newSpec);
        JavaEESpecification updatedSpec = new JavaEESpecification(Integer.parseInt(specID), newDesc, "Security", "1.0");

        cut.updateJSRDescription(updatedSpec);
        JavaEESpecification spec = cut.get(specID);

        assertNotNull("Spec was null!", spec);
        assertEquals("Description was not updated", spec.getDescription(), newDesc);
        assertEquals("Wrong spec id", spec.getJsrId(), new Integer(specID));
        assertEquals("Wrong spec name", spec.getName(), "Security");
        assertEquals("Wrong spec version", spec.getVersion(), "1.0");
    }
}
