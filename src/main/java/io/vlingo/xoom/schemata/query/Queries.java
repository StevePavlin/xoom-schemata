// Copyright © 2012-2021 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.xoom.schemata.query;

import io.vlingo.xoom.actors.Stage;
import io.vlingo.xoom.symbio.store.object.ObjectStore;

public class Queries {
  // TODO: If requires a greater number of instances
  // consider using io.vlingo.xoom.actors.RoundRobinRouter.

  private static OrganizationQueries organizationQueries;
  private static UnitQueries unitQueries;
  private static ContextQueries contextQueries;
  private static SchemaQueries schemaQueries;
  private static SchemaVersionQueries schemaVersionQueries;
  private static CodeQueries codeQueries;
  private static TypeResolverQueries typeResolverQueries;

  public static void startAll(final Stage stage, final ObjectStore objectStore) {
    organizationQueries = stage.actorFor(OrganizationQueries.class, OrganizationQueriesActor.class, objectStore);
    unitQueries = stage.actorFor(UnitQueries.class, UnitQueriesActor.class, objectStore);
    contextQueries = stage.actorFor(ContextQueries.class, ContextQueriesActor.class, objectStore);
    schemaQueries = stage.actorFor(SchemaQueries.class, SchemaQueriesActor.class, objectStore);
    schemaVersionQueries = stage.actorFor(SchemaVersionQueries.class, SchemaVersionQueriesActor.class, objectStore);
    codeQueries = stage.actorFor(CodeQueries.class, CodeQueriesActor.class, schemaVersionQueries);
    typeResolverQueries = stage.actorFor(TypeResolverQueries.class, TypeResolverQueriesActor.class, schemaVersionQueries);
  }

  public static OrganizationQueries forOrganizations() {
    return organizationQueries;
  }

  public static UnitQueries forUnits() {
    return unitQueries;
  }

  public static ContextQueries forContexts() {
    return contextQueries;
  }

  public static SchemaQueries forSchemas() {
    return schemaQueries;
  }

  public static SchemaVersionQueries forSchemaVersions() {
    return schemaVersionQueries;
  }

  public static CodeQueries forCode() {
    return codeQueries;
  }

  public static TypeResolverQueries forTypeResolver() {
    return typeResolverQueries;
  }
}
