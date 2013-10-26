package de.rjo.jarchecker.config;

import java.util.Iterator;

import de.rjo.jarchecker.checker.Checker;

/**
 * Die Interface fuer das Konfiguration.
 */
public interface Config {

	Iterator<Checker> checks();

}