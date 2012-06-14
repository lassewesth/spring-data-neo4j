/**
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.neo4j.annotation;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class City
{
    @GraphId
    private Long id;

    private String name;

    @RelatedToVia
    private Set<TransportInfrastructure> existingMetroLines = new HashSet<TransportInfrastructure>();

    @RelatedToVia(type = "planned")
    private Set<TransportInfrastructure> plannedMetroLines = new HashSet<TransportInfrastructure>();

    public City()
    {
    }

    public City( String name )
    {

        this.name = name;
    }

    public void has( MetroLine... metroLines )
    {
        addTo( metroLines, existingMetroLines );
    }

    public void plans( MetroLine... metroLines )
    {
        addTo( metroLines, plannedMetroLines );
    }

    public Long getId()
    {
        return id;
    }

    public Set<Long> getIdsOfExistingMetroLines()
    {
        return getMetroLineIds( existingMetroLines );
    }

    public Set<Long> getIdsOfPlannedMetroLines()
    {
        return getMetroLineIds( plannedMetroLines );
    }

    private void addTo( MetroLine[] metroLines, Set<TransportInfrastructure> transportInfrastructures )
    {
        for ( MetroLine metroLine : metroLines )
        {
            transportInfrastructures.add( new TransportInfrastructure( this, metroLine ) );
        }
    }

    private Set<Long> getMetroLineIds( Set<TransportInfrastructure> metroLines )
    {
        HashSet<Long> ids = new HashSet<Long>();

        for ( TransportInfrastructure existingMetroLine : metroLines )
        {
            ids.add( existingMetroLine.getMetroLine().getId() );
        }

        return ids;
    }
}
