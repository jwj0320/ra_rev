package api;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import ru.avicomp.ontapi.OntManagers;
import ru.avicomp.ontapi.OntologyModel;

public class OntologyFunc {
    OWLOntologyManager manager = OntManagers.createONT();
	OWLDataFactory factory = manager.getOWLDataFactory();
	File file = new File(this.getClass().getResource("").getPath(),"../../../owl/CB_PDO_V11_OR.owl");
	// File file = new File(this.getClass().getResource("").getPath(),"../../../owl/CB_PDO_V7 (1).owl");
	File file2 = new File(this.getClass().getResource("").getPath(),"../../../owl/CB_PDO_V7.owl");
	
	OWLOntology ontology;
	OntologyModel o;

    public OntologyFunc(){
        System.out.println("Load Ontology: " + LoadOntology());
    }

    public boolean LoadOntology() {
		try {
			ontology = manager.loadOntologyFromOntologyDocument(file); // file
			o = (OntologyModel)ontology;
			return true;	
		}
		catch (OWLOntologyCreationException e) {
			System.err.println("Error creating OWL ontology: " + e.getMessage());
			//	System.exit(-1);
			return false;
		} 	
	}

	public OntologyFunc(String str){
        System.out.println("Load Ontology2: " + LoadOntology2());
    }

	public boolean LoadOntology2() {
		try {
			ontology = manager.loadOntologyFromOntologyDocument(file2);
			o = (OntologyModel)ontology;
			return true;	
		}
		catch (OWLOntologyCreationException e) {
			System.err.println("Error creating OWL ontology: " + e.getMessage());
			//	System.exit(-1);
			return false;
		} 	
	}

	// public ArrayList<String> LoadSWInfo(String SWvector){
	// 	ArrayList<String> swInfo = new ArrayList<String>();
	// 	String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
	// 			+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
	// 			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
	// 			+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
	// 			+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
	// 			+ "SELECT ?s_p_l ?t_l\n"
	// 			+ "WHERE { \n"
	// 			+ "  ?s rdfs:label \""+ SWvector +"\".\n"
	// 			+ "  ?s PDO:work_in_platforms ?s_p."
	// 			+ "  ?s_p rdfs:label ?s_p_l."
	// 			+ "  ?s PDO:perform_techniques ?t.\n"
	// 			+ "  ?t rdfs:label ?t_l.\n"
	// 			+ "}";
	// 	Query query = QueryFactory.create(queryString);
	// 	QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
	// 	ResultSet res = qe.execSelect();
	// 	while (res.hasNext()) {
	// 		QuerySolution qs = res.next();
	// 		String s1 = qs.get("s_p_l").toString();
	// 		String s2 = qs.get("t_l").toString();
	// 		swInfo.add(s1);
	// 		swInfo.add(s2);
	// 	}
		
	// 	return swInfo;
	// }

	public ArrayList<String> LoadAllOrg() {
		ArrayList<String> listvector = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?a_l\n"
				+ "WHERE { \n"
				+ "  ?a rdf:type PDO:Organization.\n"
				+ "  ?a rdfs:label ?a_l.\n"
				+ "}"
                + "ORDER BY ASC(?a_l)";
		
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("a_l").toString();
			
			listvector.add(s1);
		}	
		
		return listvector;
	}

	public ArrayList<String> LoadTacticFromTech(String tech){
		ArrayList<String> list = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?tactic_l\n"
				+ "WHERE { \n"
				+ "  ?tech rdfs:label \""+ tech +"\".\n"
				+ "  ?tech PDO:constitute_tactics ?tactic.\n"
				+ "  ?tactic rdfs:label ?tactic_l.\n"
				+ "}"
				+ "ORDER BY ASC(?tactic_l)";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("tactic_l").toString();
			list.add(s1);
		}
		
		return list;
	}

	public ArrayList<String> LoadCAPECFromTech(String tech){
		ArrayList<String> list = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?CAPEC_l\n"
				+ "WHERE { \n"
				+ "  ?tech rdfs:label \""+ tech +"\".\n"
				+ "  ?tech PDO:have_CAPEC ?CAPEC.\n"
				+ "  ?CAPEC rdfs:label ?CAPEC_l.\n"
				+ "}"
				+ "ORDER BY ASC(?CAPEC_l)";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("CAPEC_l").toString();
			list.add(s1);
		}
		
		return list;
	}

	public ArrayList<String> LoadCVEFromCWEList(ArrayList<String> CWEList){
		Set<String> set = new LinkedHashSet<>();
		for(String CWE:CWEList){
			set.addAll(LoadCVEFromCWE(CWE));
		}
		return new ArrayList<String>(set);
	}

	public ArrayList<String> LoadCVEFromCWE(String CWE){
		ArrayList<String> list = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?CVE_l\n"
				+ "WHERE { \n"
				+ "  ?CWE rdfs:label \""+ CWE +"\".\n"
				+ "  ?CWE PDO:have_CVE ?CVE.\n"
				+ "  ?CVE rdfs:label ?CVE_l.\n"
				+ "}"
				+ "ORDER BY ASC(?CVE_l)";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("CVE_l").toString();
			list.add(s1);
		}
		
		return list;
	}

	public ArrayList<String> LoadCWEFromCAPECList(ArrayList<String> CAPECList){
		Set<String> set = new LinkedHashSet<>();
		for(String CAPEC:CAPECList){
			set.addAll(LoadCWEFromCAPEC(CAPEC));
		}
		return new ArrayList<String>(set);
	}

	public ArrayList<String> LoadCWEFromCAPEC(String CAPEC){
		ArrayList<String> list = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?CWE_l\n"
				+ "WHERE { \n"
				+ "  ?CAPEC rdfs:label \""+ CAPEC +"\".\n"
				+ "  ?CAPEC PDO:have_CWE ?CWE.\n"
				+ "  ?CWE rdfs:label ?CWE_l.\n"
				+ "}"
				+ "ORDER BY ASC(?CWE_l)";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("CWE_l").toString();
			list.add(s1);
		}
		
		return list;
	}


	public ArrayList<String> LoadMitigationFromTech(String tech){
		ArrayList<String> list = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?miti_l\n"
				+ "WHERE { \n"
				+ "  ?tech rdfs:label \""+ tech +"\".\n"
				+ "  ?tech PDO:mitigated_by ?miti.\n"
				+ "  ?miti rdfs:label ?miti_l.\n"
				+ "}"
				+ "ORDER BY ASC(?miti_l)";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("miti_l").toString();
			list.add(s1);
		}
		
		return list;
	}


	public ArrayList<String> LoadBPFromOrg(String org){
		ArrayList<String> list = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?b_l\n"
				+ "WHERE { \n"
				+ "  ?o rdfs:label \""+ org +"\".\n"
				+ "  ?o PDO:have_business_processes ?b.\n"
				+ "  ?b rdfs:label ?b_l.\n"
				+ "}"
				+ "ORDER BY ASC(?b_l)";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("b_l").toString();
			list.add(s1);
		}
		
		return list;
	}

	public ArrayList<String> LoadRoleFromBP(String bp){
		ArrayList<String> list = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?r_l\n"
				+ "WHERE { \n"
				+ "  ?b rdfs:label \""+ bp +"\".\n"
				+ "  ?b PDO:performed_by_roles ?r.\n"
				+ "  ?r rdfs:label ?r_l.\n"
				+ "}"
				+ "ORDER BY ASC(?r_l)";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("r_l").toString();
			list.add(s1);
		}
		
		return list;
	}

	public ArrayList<String> LoadPersonFromRole(String role){
		ArrayList<String> list = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?p_l\n"
				+ "WHERE { \n"
				+ "  ?r rdfs:label \""+ role +"\".\n"
				+ "  ?r PDO:include_person ?p.\n"
				+ "  ?p rdfs:label ?p_l.\n"
				+ "}"
				+ "ORDER BY ASC(?p_l)";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("p_l").toString();
			list.add(s1);
		}
		
		return list;
	}

	public ArrayList<String> LoadSWFromRole(String role){
		ArrayList<String> list = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?s_l\n"
				+ "WHERE { \n"
				+ "  ?r rdfs:label \""+ role +"\".\n"
				+ "  ?r PDO:utilize_software ?s.\n"
				+ "  ?s rdfs:label ?s_l.\n"
				+ "}"
				+ "ORDER BY ASC(?s_l)";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("s_l").toString();
			list.add(s1);
		}
		
		return list;
	}

	public ArrayList<String> LoadPlatformFromSW(String sw){
		ArrayList<String> list = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?p_l\n"
				+ "WHERE { \n"
				+ "  ?r rdfs:label \""+ sw +"\".\n"
				+ "  ?r PDO:work_in_platforms ?p.\n"
				+ "  ?p rdfs:label ?p_l.\n"
				+ "}"
				+ "ORDER BY ASC(?p_l)";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("p_l").toString();
			list.add(s1);
		}
		
		return list;
	}

	public ArrayList<String> LoadHardwareFromSW(String sw){
		ArrayList<String> list = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?h_l\n"
				+ "WHERE { \n"
				+ "  ?r rdfs:label \""+ sw +"\".\n"
				+ "  ?r PDO:work_in_hardware ?h.\n"
				+ "  ?h rdfs:label ?h_l.\n"
				+ "}"
				+ "ORDER BY ASC(?h_l)";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("h_l").toString();
			list.add(s1);
		}
		
		return list;
	}

	public ArrayList<String> LoadDataFromSW(String sw){
		ArrayList<String> list = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?d_l\n"
				+ "WHERE { \n"
				+ "  ?r rdfs:label \""+ sw +"\".\n"
				+ "  ?r PDO:access_data ?d.\n"
				+ "  ?d rdfs:label ?d_l.\n"
				+ "}"
				+ "ORDER BY ASC(?d_l)";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("d_l").toString();
			list.add(s1);
		}
		
		return list;
	}

	public ArrayList<String> LoadMDFromSW(String sw){
		ArrayList<String> list = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?m_l\n"
				+ "WHERE { \n"
				+ "  ?r rdfs:label \""+ sw +"\".\n"
				+ "  ?r PDO:accessed_by_mobile_device ?m.\n"
				+ "  ?m rdfs:label ?m_l.\n"
				+ "}"
				+ "ORDER BY ASC(?m_l)";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("m_l").toString();
			list.add(s1);
		}
		
		return list;
	}

	public ArrayList<String> LoadDAFromHW(String hw){
		ArrayList<String> list = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?d_l\n"
				+ "WHERE { \n"
				+ "  ?r rdfs:label \""+ hw +"\".\n"
				+ "  ?r PDO:stored_in_dedicated_area ?d.\n"
				+ "  ?d rdfs:label ?d_l.\n"
				+ "}"
				+ "ORDER BY ASC(?d_l)";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("d_l").toString();
			list.add(s1);
		}
		
		return list;
	}

	
	

    public ArrayList<String> LoadTechniquesFromSW(String SWvector){
		ArrayList<String> techlist = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?t_l\n"
				+ "WHERE { \n"
				+ "  ?s rdfs:label \""+ SWvector +"\".\n"
				+ "  ?s PDO:perform_techniques ?t.\n"
				+ "  ?t rdfs:label ?t_l.\n"
				+ "}";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("t_l").toString();
			techlist.add(s1);
		}
		
		return techlist;
	}

    public ArrayList<String[]> LoadAllGroup() {
		ArrayList<String[]> listvector = new ArrayList<String[]>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?a_l\n"
				+ "WHERE { \n"
				+ "  ?a rdf:type PDO:Attack_Groups.\n ?a rdfs:label ?a_l.\n"
				+ "}"
                + "ORDER BY ASC(?a_l)";
		
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("a_l").toString();
			
			listvector.add(new String[]{s1});
		}	
		
		return listvector;
	}
    
    public ArrayList<String[]> LoadAllSW() {
		ArrayList<String[]> listvector = new ArrayList<String[]>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?a_l\n"
				+ "WHERE { \n"
				+ "  ?a rdf:type PDO:Attack_Software.\n" 
				+ "  ?a rdfs:label ?a_l.\n"
				+ "}"
                + "ORDER BY ASC(?a_l)";
		
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("a_l").toString();
			
			listvector.add(new String[]{s1});
		}	
		
		return listvector;
	}
    
	public ArrayList<String[]> LoadAllTactics() {
		ArrayList<String[]> listvector = new ArrayList<String[]>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?a_l\n"
				+ "WHERE { \n"
				+ "  ?a rdf:type PDO:Attack_Tactics.\n ?a rdfs:label ?a_l.\n"
				+ "}"
                + "ORDER BY ASC(?a_l)";
		
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("a_l").toString();
			
			listvector.add(new String[]{s1});
		}	
		
		return listvector;
	}

    public ArrayList<String[]> LoadAllTech() {
		ArrayList<String[]> listvector = new ArrayList<String[]>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?a_l\n"
				+ "WHERE { \n"
				+ "  ?a rdf:type PDO:Attack_Techniques.\n ?a rdfs:label ?a_l.\n"
				+ "}"
                + "ORDER BY ASC(?a_l)";
		
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("a_l").toString();
			
			listvector.add(new String[]{s1});
		}	
		
		return listvector;
	}

    public ArrayList<String[]> LoadSWPlatform(String sw) {
		ArrayList<String[]> listvector = new ArrayList<String[]>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?a_p_l \n"
				+ "WHERE { \n"
				+ "  ?a rdfs:label \"" + sw +"\".\n"+ "?a PDO:work_in_platforms"+" ?a_p.\n"
				+ "  ?a_p rdfs:label ?a_p_l.\n"
				+ "}"
                + "ORDER BY ASC(?a_p_l)";
		
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("a_p_l").toString();
			
			listvector.add(new String[]{s1});
		}	
		
		return listvector;
	}

    public ArrayList<String[]> LoadSWTech(String sw) {
		ArrayList<String[]> listvector = new ArrayList<String[]>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?a_t_l \n"
				+ "WHERE { \n"
				+ "  ?a rdfs:label \"" + sw +"\".\n"+ "?a PDO:perform_techniques"+" ?a_t.\n"
				+ "  ?a_t rdfs:label ?a_t_l.\n"
				+ "}"
                + "ORDER BY ASC(?a_p_l)";
		
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("a_t_l").toString();
			
			listvector.add(new String[]{s1});
		}	
		
		return listvector;
	}
    
    public ArrayList<String> LoadGroupTechnique(String group) {
		ArrayList<String> listvector = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?t_l \n"
				+ "WHERE { "
				+ "?g rdfs:label \""+ group +"\".\n"
				+ "?g PDO:use_techniques ?t.\n"
				+ "?t rdfs:label ?t_l. \n"
				+ "}";
		
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("t_l").toString();
			
			listvector.add(s1);
		}	
		
		return listvector;
	}

	public ArrayList<String> LoadGroupSW(String group) {
		ArrayList<String> listvector = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?s_l \n"
				+ "WHERE { "
				+ "?g rdfs:label \""+ group +"\".\n"
				+ "?g PDO:use_software ?s.\n"
				+ "?s rdfs:label ?s_l. \n"
				+ "}";
		
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("s_l").toString();
			
			listvector.add(s1);
		}	
		
		return listvector;
	}

	public ArrayList<String> LoadTechSW(String tech) {
		ArrayList<String> listvector = new ArrayList<String>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?s_l \n"
				+ "WHERE { "
				+ "?g rdfs:label \""+ tech +"\".\n"
				+ "?g PDO:performed_by ?s.\n"
				+ "?s rdfs:label ?s_l. \n"
				+ "}";
		
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			String s1 = qs.get("s_l").toString();
			
			listvector.add(s1);
		}	
		System.out.println(listvector.size());
		return listvector;
	}

	
	public ArrayList<String[]> LoadAssetInfo(String assetId){
		ArrayList<String[]> list = new ArrayList<String[]>();
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX PDO: <http://www.semanticweb.org/PDO#>\n"
				+ "PREFIX CBSAO: <http://www.semanticweb.org/CBSAO#>\n"
				+ "SELECT ?type ?name ?desc\n"
				+ "WHERE { \n"
				+ "  ?asset rdfs:label \""+ assetId +"\".\n"
				+ "  ?asset owl:type ?type.\n"
				+ "  ?asset owl:name ?name.\n"
				+ "  ?asset owl:description ?desc.\n"
				+ "}"
				+ "ORDER BY ASC(?type)";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, o.asGraphModel());
		ResultSet res = qe.execSelect();
		while (res.hasNext()) {
			QuerySolution qs = res.next();
			list.add(new String[]{
				qs.get("type").toString(),
				qs.get("name").toString(),
				qs.get("desc").toString()
			});
		}
		
		return list;
	}
   
}
