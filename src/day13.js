'use strict';

var fs = require('fs');
var array = fs.readFileSync('./src/input13_1.txt').toString().split("\n");
// var array = fs.readFileSync('./src/Day13_test.txt').toString().split("\n");

const packetPairs = []
const allPackets = []
let currentPair = []

for (let line of array) {
    if (line == '') {
        packetPairs.push(currentPair)
        currentPair = []
    } else {
        const lineJson = JSON.parse(line)
        currentPair.push(lineJson)
        allPackets.push(lineJson)
    }
}

function checkPacketPairOrder(left, right) {
    if (typeof left === 'number' && typeof right === 'number') {
        return left < right ? 'inorder' : left > right ? 'outoforder' : 'neutral'
    }
    if (Array.isArray(left) && Array.isArray(right)) {
        if (left.length === 0 && right.length === 0) return 'neutral'
        if (left.length === 0 && right.length !== 0) return 'inorder'
        if (left.length !== 0 && right.length === 0) return 'outoforder'
        const firstResult = checkPacketPairOrder(left[0], right[0])
        if (firstResult === 'neutral') {
            return checkPacketPairOrder(left.slice(1), right.slice(1))
        }
        return firstResult
    }
    if (Array.isArray(left) && typeof right === 'number') {
        return checkPacketPairOrder(left, [right])
    }
    if (typeof left === 'number' && Array.isArray(right)) {
        return checkPacketPairOrder([left], right)
    }
}

function part1() {
    let rightOrderIndexesSum = 0
    for (let [i, packetPair] of packetPairs.entries()) {
        if (checkPacketPairOrder(packetPair[0], packetPair[1]) === 'inorder') {
            rightOrderIndexesSum += (i + 1)
        }
    }
    console.log(rightOrderIndexesSum)
}

function part2() {
    allPackets.push([[2]], [[6]])
    allPackets.sort((p1, p2) => checkPacketPairOrder(p1, p2) === 'inorder' ? -1 : 1)
    console.log((allPackets.findIndex((e) => JSON.stringify(e) == '[[2]]') + 1) * (allPackets.findIndex((e) => JSON.stringify(e) == '[[6]]') + 1))
}

part1()
part2()