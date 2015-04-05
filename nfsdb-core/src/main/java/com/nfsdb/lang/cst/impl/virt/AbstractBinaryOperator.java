/*
 * Copyright (c) 2014-2015. Vlad Ilyushchenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nfsdb.lang.cst.impl.virt;

import com.nfsdb.lang.cst.RecordMetadata;
import com.nfsdb.lang.cst.RecordSourceState;
import com.nfsdb.storage.ColumnType;

public abstract class AbstractBinaryOperator extends AbstractVirtualColumn {
    protected VirtualColumn lhs;
    protected VirtualColumn rhs;

    public AbstractBinaryOperator(ColumnType type) {
        super(type);
    }

    @Override
    public void configure(RecordMetadata metadata, RecordSourceState state) {
        super.configure(metadata, state);
        lhs.configure(metadata, state);
        rhs.configure(metadata, state);
    }

    public void setLhs(VirtualColumn lhs) {
        this.lhs = lhs;
    }

    public void setRhs(VirtualColumn rhs) {
        this.rhs = rhs;
    }
}
